package like.heocholi.spartaeats.dto;

import like.heocholi.spartaeats.entity.Pick;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PickResponseDto {
    private long storeId;
    private String name;
    private String address;

    public PickResponseDto(Pick pick) {
        storeId = pick.getStore().getId();
        this.name = pick.getStore().getName();
        this.address = pick.getStore().getAddress();
    }

}
