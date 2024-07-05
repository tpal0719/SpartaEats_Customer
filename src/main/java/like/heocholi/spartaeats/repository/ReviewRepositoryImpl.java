package like.heocholi.spartaeats.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import like.heocholi.spartaeats.entity.QLike;
import like.heocholi.spartaeats.entity.QReview;
import like.heocholi.spartaeats.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Review> getReviewCustomerLikeWithPage(Long customerId, Pageable pageable) {
        QReview qReview = QReview.review;
        QLike qLike = QLike.like;

        List<Review>  reviews = jpaQueryFactory.select(qReview)
                .from(qReview)
                .leftJoin(qLike).on(qReview.id.eq(qLike.review.id))
                .where(qLike.customer.id.eq(customerId).and(qLike.isLike.isTrue()))
                .orderBy(qReview.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return reviews;
    }

    @Override
    public Long getLikeCountByCustomer(Long customerId) {
        QReview qReview = QReview.review;
        QLike qLike = QLike.like;

        Long count = jpaQueryFactory
                .select(qLike.count())
                .from(qReview)
                .leftJoin(qLike).on(qReview.id.eq(qLike.review.id))
                .where(qLike.customer.id.eq(customerId).and(qLike.isLike.isTrue()))
                .fetchOne();

        return count;
    }
}
