package like.heocholi.spartaeats.repository;

import like.heocholi.spartaeats.entity.Customer;
import like.heocholi.spartaeats.entity.Pick;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PickRepository extends JpaRepository<Pick, Long> {
    Page<Pick> findAllByCustomer(Customer customer, Pageable pageable);
}
