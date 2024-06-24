package like.heocholi.spartaeats.dto;

import like.heocholi.spartaeats.entity.Pick;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PickResponseDto {
    private String name;
    private String address;

    public PickResponseDto(Pick pick) {
        this.name = pick.getStore().getName();
        this.address = pick.getStore().getAddress();
    }

}
