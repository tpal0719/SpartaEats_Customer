package like.heocholi.spartaeats.dto;

import like.heocholi.spartaeats.entity.Customer;
import lombok.Getter;

@Getter
public class CustomerResponseDTO {
    private String customerId;
    private String name;
    private String address;
    private String bio;

    private Long storePickCount;
    private Long reviewLikeCount;

    public CustomerResponseDTO(Customer customer, Long storeLikeCount, Long reviewLikeCount) {
        this.customerId = customer.getUserId();
        this.name = customer.getName();
        this.address = customer.getAddress();
        this.bio = customer.getBio();

        this.storePickCount = storeLikeCount;
        this.reviewLikeCount = reviewLikeCount;
    }
}
