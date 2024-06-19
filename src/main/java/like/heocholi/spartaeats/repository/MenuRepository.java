package like.heocholi.spartaeats.repository;

import like.heocholi.spartaeats.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
}
