package like.heocholi.spartaeats.repository;

import like.heocholi.spartaeats.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
