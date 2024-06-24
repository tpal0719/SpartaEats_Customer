package like.heocholi.spartaeats.dto;

import like.heocholi.spartaeats.entity.Menu;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MenuResponseDto {

    private Long id;
    private String name;
    private int price;
    private String storeName;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


    public MenuResponseDto(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
        this.storeName = menu.getStore().getName();

        this.createdAt = menu.getCreatedAt();
        this.modifiedAt = menu.getModifiedAt();
    }


}
