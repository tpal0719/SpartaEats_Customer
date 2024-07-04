package like.heocholi.spartaeats.repository;

import like.heocholi.spartaeats.entity.Store;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StoreQueryDslRepository {

    List<Store> getStoreCustomerPickWithPage(Long userId, Pageable pageable);

    Long getPickCountByCustomer(Long userId);
}
