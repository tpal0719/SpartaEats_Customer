package like.heocholi.spartaeats.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import like.heocholi.spartaeats.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Store> getStoreCustomerPickWithPage(Long customerId, Pageable pageable) {
        QStore qStore = QStore.store;
        QPick qPick = QPick.pick;

        List<Store> storeList = jpaQueryFactory
                .select(qStore)
                .from(qStore)
                .join(qPick).on(qStore.id.eq(qPick.store.id))
                .where(qPick.customer.id.eq(customerId).and(qPick.isPick.isTrue()))
                .orderBy(qStore.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return storeList;
    }

    @Override
    public List<Store> getStoreCustomerPickWithPageOrderByManagerId(Long customerId, Pageable pageable) {
        QStore qStore = QStore.store;
        QPick qPick = QPick.pick;

        List<Store> storeList = jpaQueryFactory.selectFrom(qStore)
                .join(qStore.pickList, qPick)
                .where(qPick.customer.id.eq(customerId).and(qPick.isPick.isTrue()))
                .orderBy(qStore.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return storeList;
    }

    @Override
    public Long getPickCountByCustomer(Long customerId) {
        QStore qStore = QStore.store;
        QPick qPick = QPick.pick;

        Long count = jpaQueryFactory
                .select(qPick.count())
                .from(qStore)
                .join(qPick).on(qStore.id.eq(qPick.store.id))
                .where(qPick.customer.id.eq(customerId).and(qPick.isPick.isTrue()))
                .fetchOne();

        return count;
    }
}
