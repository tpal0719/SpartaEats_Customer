package like.heocholi.spartaeats.repository;

import like.heocholi.spartaeats.entity.Customer;
import like.heocholi.spartaeats.entity.PasswordHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, Long> {
    List<PasswordHistory> findTop3ByCustomerOrderByCreatedAtDesc(Customer customer);
}
