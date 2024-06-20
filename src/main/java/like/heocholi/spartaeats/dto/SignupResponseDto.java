package like.heocholi.spartaeats.dto;

import like.heocholi.spartaeats.entity.Customer;
import lombok.Getter;

@Getter
public class SignupResponseDto {
    private String userId;
    private String name;
    private String address;

    public SignupResponseDto(Customer customer) {
        this.userId = customer.getUserId();
        this.name = customer.getName();
        this.address = customer.getName();
    }
}
