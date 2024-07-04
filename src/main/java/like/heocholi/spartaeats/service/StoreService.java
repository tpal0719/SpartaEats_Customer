package like.heocholi.spartaeats.service;

import like.heocholi.spartaeats.constants.ErrorType;

import like.heocholi.spartaeats.dto.PickStoreResponseDto;
import like.heocholi.spartaeats.constants.RestaurantType;
import like.heocholi.spartaeats.dto.StorePageResponseDto;
import like.heocholi.spartaeats.dto.StoreResponseDto;
import like.heocholi.spartaeats.entity.Customer;
import like.heocholi.spartaeats.entity.Pick;
import like.heocholi.spartaeats.entity.Store;
import like.heocholi.spartaeats.exception.PageException;
import like.heocholi.spartaeats.exception.StoreException;
import like.heocholi.spartaeats.repository.PickRepository;
import like.heocholi.spartaeats.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;
    private final PickRepository pickRepository;

    // 가게 상세 조회
    public StoreResponseDto readStore(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new StoreException(ErrorType.NOT_FOUND_STORE));

        return new StoreResponseDto(store);
    }

    // 가게 전체 조회
    public StorePageResponseDto getStorePageByType(String type, Integer page) {
        RestaurantType restaurantType = checkValidateType(type);
        Pageable pageable = PageRequest.of(page - 1, 5);

        Page<Store> storePageList = storeRepository.findByTypeGroupedByStoreOrderByOrderCountDesc(restaurantType, pageable);

        checkValidatePage(page, storePageList);

        return new StorePageResponseDto(page, storePageList);
    }

    // 찜한 가게 조회
    public Page<StoreResponseDto> getStoreCustomerPickWithPage(Long customerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Store> stores = storeRepository.getStoreCustomerPickWithPage(customerId, pageable);
        List<StoreResponseDto> storeResponseDtoList = stores.stream().map(StoreResponseDto::new).toList();

        Long count = getPickCountByCustomer(customerId);

        return new PageImpl<>(storeResponseDtoList,pageable,count);
    }

    // 찜한 가게 조회(음식점 점주 아이디순)
    public Page<StoreResponseDto> getStoreCustomerPickWithPageOrderByManagerId(Long customerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Store> stores = storeRepository.getStoreCustomerPickWithPage(customerId, pageable);
        List<StoreResponseDto> storeResponseDtoList = stores.stream().map(StoreResponseDto::new).toList();

        Long count = getPickCountByCustomer(customerId);

        return new PageImpl<>(storeResponseDtoList,pageable,count);
    }


    private RestaurantType checkValidateType(String type) {
        RestaurantType restaurantType;
        try {
            restaurantType = RestaurantType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new StoreException(ErrorType.INVALID_TYPE);
        }

        return restaurantType;
    }

    // 찜하기 관리
    @Transactional
    public PickStoreResponseDto managePicks(Long storeId, Customer customer) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new StoreException(ErrorType.NOT_FOUND_STORE));
        Pick pick = updatePick(store, customer);
        return new PickStoreResponseDto(store.getName(), pick.isPick());

    }

    // 페이지 유효성 검사
    private static void checkValidatePage(Integer page, Page<Store> storePageList) {
        if (storePageList.getTotalElements() == 0) {
            throw new StoreException(ErrorType.NOT_FOUND_STORES);
        }

        if (page > storePageList.getTotalPages() || page < 1) {
            throw new PageException(ErrorType.INVALID_PAGE);
        }
    }

    public Pick updatePick(Store store, Customer customer) {
        Pick pick = pickRepository.findByStoreAndCustomer(store, customer);

        if (pick != null) {
            pick.update();
        } else{
            pick = Pick.builder()
                    .customer(customer)
                    .store(store)
                    .isPick(true)
                    .build();

            pickRepository.save(pick);
        }

        return pick;
    }






    /* Utils */

    // 찜한 가게 갯수
    public Long getPickCountByCustomer(Long userId) {
        return storeRepository.getPickCountByCustomer(userId);
    }

}
