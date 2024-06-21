package like.heocholi.spartaeats.repository;

import like.heocholi.spartaeats.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query("SELECT s FROM Store s LEFT JOIN s.orders o GROUP BY s.id ORDER BY COUNT(o) DESC")
    Page<Store> findAllGroupedByStoreOrderByOrderCountDesc(Pageable pageable);
}
