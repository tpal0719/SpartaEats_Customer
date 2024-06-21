package like.heocholi.spartaeats.service;

import like.heocholi.spartaeats.constants.ErrorType;
import like.heocholi.spartaeats.dto.StorePageResponseDto;
import like.heocholi.spartaeats.dto.StoreResponseDto;
import like.heocholi.spartaeats.entity.Store;
import like.heocholi.spartaeats.exception.ContentNotFoundException;
import like.heocholi.spartaeats.exception.StoreException;
import like.heocholi.spartaeats.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    public StoreResponseDto readStore(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new StoreException(ErrorType.NOT_FOUND_STORE));

        return new StoreResponseDto(store);
    }


    public StorePageResponseDto getStorePage(Integer page) {
        Pageable pageable = PageRequest.of(page-1, 5);
        Page<Store> storePageList = storeRepository.findAllGroupedByStoreOrderByOrderCountDesc(pageable);

        checkValidatePage(page, storePageList);

        return new StorePageResponseDto(page, storePageList);

    }

    private static void checkValidatePage(Integer page, Page<Store> storePageList) {
        if (storePageList.getTotalElements() == 0) {
            throw new ContentNotFoundException("가게가 존재하지 않습니다.");
        }

        if (page > storePageList.getTotalPages() || page < 1) {
            throw new ContentNotFoundException("페이지가 존재하지 않습니다.");
        }
    }
}
