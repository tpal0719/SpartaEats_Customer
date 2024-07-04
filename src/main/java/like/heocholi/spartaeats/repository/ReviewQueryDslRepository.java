package like.heocholi.spartaeats.repository;

import like.heocholi.spartaeats.entity.Review;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewQueryDslRepository {

    List<Review> getReviewCustomerLikeWithPage(Long customerId, Pageable pageable);

    Long getLikeCountByCustomer(Long customerId);

}
