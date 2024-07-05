package like.heocholi.spartaeats.repository;

import like.heocholi.spartaeats.constants.RestaurantType;
import like.heocholi.spartaeats.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoreRepository extends JpaRepository<Store, Long>, StoreRepositoryCustom {
    @Query("SELECT s FROM Store s LEFT JOIN s.orders o WHERE s.type = :type GROUP BY s.id ORDER BY COUNT(o) DESC")
    Page<Store> findByTypeGroupedByStoreOrderByOrderCountDesc(@Param("type") RestaurantType type, Pageable pageable);
}
