package like.heocholi.spartaeats.dto;

import like.heocholi.spartaeats.entity.Store;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class StorePageResponseDto {
    private Integer currentPage;
    private Long totalElements;
    private Integer totalPages;
    private Integer size;
    private List<StoreResponseDto> storeList;

    public StorePageResponseDto(Integer currentPage, Page<Store> storePageList) {
        this.currentPage = currentPage;
        this.totalElements = storePageList.getTotalElements();
        this.totalPages = storePageList.getTotalPages();
        this.size = storePageList.getSize();
        this.storeList = storePageList.getContent().stream().map(StoreResponseDto::new).toList();
    }
}
