package like.heocholi.spartaeats.service;

import like.heocholi.spartaeats.dto.StorePageResponseDto;
import like.heocholi.spartaeats.dto.StoreResponseDto;
import like.heocholi.spartaeats.entity.Menu;
import like.heocholi.spartaeats.entity.Store;
import like.heocholi.spartaeats.repository.MenuRepository;
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
    private final MenuRepository menuRepository;

    public StoreResponseDto readStore(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new IllegalArgumentException("해당 가게가 존재하지 않습니다."));

        return new StoreResponseDto(store);
    }


    public StorePageResponseDto getStorePage(Integer page) {
        Pageable pageable = PageRequest.of(page-1, 5);
        Page<Store> storePageList = storeRepository.findAllGroupedByStoreOrderByOrderCountDesc(pageable);

        return new StorePageResponseDto(page, storePageList);

    }
}
