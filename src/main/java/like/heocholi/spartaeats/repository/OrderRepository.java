package like.heocholi.spartaeats.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import like.heocholi.spartaeats.entity.Customer;
import like.heocholi.spartaeats.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	Page<Order> findAllByCustomer(Customer customer, Pageable pageable);
}
