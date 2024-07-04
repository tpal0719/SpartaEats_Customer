package like.heocholi.spartaeats.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import like.heocholi.spartaeats.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class StoreQueryDslRespositoryImpl implements StoreQueryDslRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Store> getStoreCustomerPickWithPage(Long userId, Pageable pageable) {
        QStore qStore = QStore.store;
        QPick qPick = QPick.pick;

        List<Store> storeList = jpaQueryFactory.selectFrom(qStore)
                .leftJoin(qPick).on(qStore.id.eq(qPick.store.id))
                .orderBy(qStore.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return storeList;
    }

    @Override
    public List<Store> getStoreCustomerPickWithPageOrderByManagerId(Long userId, Pageable pageable) {
        QStore qStore = QStore.store;
        QPick qPick = QPick.pick;

        List<Store> storeList = jpaQueryFactory.selectFrom(qStore)
                .leftJoin(qPick).on(qStore.id.eq(qPick.store.id))
                .orderBy(qStore.manager.userId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return storeList;
    }

    @Override
    public Long getPickCountByCustomer(Long customerId) {
        QStore qStore = QStore.store;
        QPick qPick = QPick.pick;

        Long count = jpaQueryFactory.select(qPick.count())
                .from(qStore)
                .leftJoin(qPick).on(qStore.id.eq(qPick.store.id))
                .where(qPick.customer.id.eq(customerId).and(qPick.isPick.isTrue()))
                .fetchOne();

        return count;
    }
}
