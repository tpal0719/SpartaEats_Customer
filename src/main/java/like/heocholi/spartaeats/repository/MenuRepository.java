package like.heocholi.spartaeats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import like.heocholi.spartaeats.entity.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
}
