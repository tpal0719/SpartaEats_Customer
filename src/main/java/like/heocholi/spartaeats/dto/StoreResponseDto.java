package like.heocholi.spartaeats.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import like.heocholi.spartaeats.constants.RestaurantType;
import like.heocholi.spartaeats.entity.Menu;
import like.heocholi.spartaeats.entity.Store;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class StoreResponseDto {

    private final Long id;
    private final String name;
    private final String address;
    private final RestaurantType type;
    private final List<Menu> menuList;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;

    public StoreResponseDto(Store store, List<Menu> menuList) {
        this.id = store.getId();
        this.name = store.getName();
        this.address = store.getAddress();
        this.type = store.getType();
        this.createdAt = store.getCreatedAt();
        this.modifiedAt = store.getModifiedAt();
        this.menuList = menuList;
    }

}
