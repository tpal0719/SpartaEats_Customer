package like.heocholi.spartaeats.dto;

import like.heocholi.spartaeats.entity.Store;
import lombok.Getter;

@Getter
public class StoreListResponseDto {
    private String name;
    private String address;
    public StoreListResponseDto(Store store) {
        this.name = store.getName();
        this.address = store.getAddress();
    }

}
