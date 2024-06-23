package like.heocholi.spartaeats.service;

import like.heocholi.spartaeats.constants.ErrorType;
import like.heocholi.spartaeats.dto.StorePageResponseDto;
import like.heocholi.spartaeats.dto.StoreResponseDto;
import like.heocholi.spartaeats.entity.Store;
import like.heocholi.spartaeats.exception.PageException;
import like.heocholi.spartaeats.exception.StoreException;
import like.heocholi.spartaeats.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;

    // 가게 상세 조회
    public StoreResponseDto readStore(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new StoreException(ErrorType.NOT_FOUND_STORE));

        return new StoreResponseDto(store);
    }

    // 가게 전체 조회
    public StorePageResponseDto getStorePage(Integer page) {
        Pageable pageable = PageRequest.of(page-1, 5);
        Page<Store> storePageList = storeRepository.findAllGroupedByStoreOrderByOrderCountDesc(pageable);

        checkValidatePage(page, storePageList);

        return new StorePageResponseDto(page, storePageList);

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
}
