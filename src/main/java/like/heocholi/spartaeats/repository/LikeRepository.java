package like.heocholi.spartaeats.repository;

import like.heocholi.spartaeats.entity.Like;
import like.heocholi.spartaeats.entity.Customer;
import like.heocholi.spartaeats.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByCustomerAndReview(Customer customer, Review review);

}
