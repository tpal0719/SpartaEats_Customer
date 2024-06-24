package like.heocholi.spartaeats.dto;

import like.heocholi.spartaeats.entity.Review;
import lombok.Getter;

@Getter
public class ReviewResponseDto {

    private Long id;

    private String storeName;
    private String customerName;
    private String contents;

    private int likeCount;


    public ReviewResponseDto(Review review) {
        this.id = review.getId();
        this.storeName = review.getStore().getName();
        this.customerName = review.getCustomer().getName();
        this.contents = review.getContents();

        this.likeCount = review.getLikeCount();
    }

}
