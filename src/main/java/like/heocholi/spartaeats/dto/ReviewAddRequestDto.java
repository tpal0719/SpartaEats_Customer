package like.heocholi.spartaeats.dto;

import like.heocholi.spartaeats.entity.Customer;
import like.heocholi.spartaeats.entity.Order;
import like.heocholi.spartaeats.entity.Store;
import lombok.Getter;

@Getter
public class ReviewAddRequestDto {

    private String content;

    public ReviewAddRequestDto(String content, Order order, Store store, Customer customer) {
        this.content = content;
    }


}
