package like.heocholi.spartaeats.repository;

import like.heocholi.spartaeats.entity.Customer;
import like.heocholi.spartaeats.entity.Pick;
import like.heocholi.spartaeats.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PickRepository extends JpaRepository<Pick, Long> {
    Pick findByStoreAndCustomer(Store store, Customer customer);

    Page<Pick> findAllByCustomerAndIsPickTrue(Customer customer, Pageable pageable);
}
