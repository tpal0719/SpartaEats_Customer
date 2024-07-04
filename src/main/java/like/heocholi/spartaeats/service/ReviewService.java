package like.heocholi.spartaeats.service;

import like.heocholi.spartaeats.constants.ErrorType;
import like.heocholi.spartaeats.dto.ReviewAddRequestDto;
import like.heocholi.spartaeats.dto.ReviewResponseDto;
import like.heocholi.spartaeats.dto.ReviewUpdateRequestDto;
import like.heocholi.spartaeats.entity.Customer;
import like.heocholi.spartaeats.entity.Order;
import like.heocholi.spartaeats.entity.Review;
import like.heocholi.spartaeats.entity.Store;
import like.heocholi.spartaeats.exception.ReviewException;
import like.heocholi.spartaeats.repository.OrderRepository;
import like.heocholi.spartaeats.repository.ReviewRepository;
import like.heocholi.spartaeats.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;

    // 리뷰조회
    public List<ReviewResponseDto> getReviews(Long storeId) {

        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new ReviewException(ErrorType.NOT_FOUND_STORE)
        );
        List<ReviewResponseDto> reviewList = store.getReviews().stream().map(ReviewResponseDto::new).toList();

        return reviewList;
    }

    // 리뷰 단건조회
    public ReviewResponseDto getReview(Long storeId, Long reviewId) {

        Review review = reviewRepository.findByStoreIdAndId(storeId, reviewId).orElseThrow(
                () -> new ReviewException(ErrorType.NOT_FOUND_REVIEW)
        );

        return new ReviewResponseDto(review);
    }


    // 리뷰 작성
    @Transactional
    public ReviewResponseDto addReview(Long orderId, ReviewAddRequestDto requestDto, Customer customer) {

        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new ReviewException(ErrorType.NOT_FOUND_ORDER)
        );

        if (!customer.getId().equals(order.getCustomer().getId())) {
            throw new ReviewException(ErrorType.INVALID_ORDER_CUSTOMER);
        }

        Review review = Review.builder()
                .order(order)
                .store(order.getStore())
                .customer(order.getCustomer())
                .contents(requestDto.getContents())
                .build();

        reviewRepository.save(review);

        return new ReviewResponseDto(review);
    }

    // 리뷰 수정
    @Transactional
    public ReviewResponseDto updateReview(Long reviewId, ReviewUpdateRequestDto requestDto, Customer customer) {

        Review review = findReviewByIdAndCustomercheck(reviewId, customer);

        review.update(requestDto.getContents());

        return new ReviewResponseDto(review);
    }

    // 리뷰 삭제
    @Transactional
    public Long deleteReview(Long reviewId, Customer customer) {

        Review review = findReviewByIdAndCustomercheck(reviewId, customer);

        reviewRepository.delete(review);

        return review.getId();
    }

    // Customer 가 좋아요한 Review만 조회
    public Page<ReviewResponseDto> getReviewCustomerLikeWithPage(Long customerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Review> reviews = reviewRepository.getReviewCustomerLikeWithPage(customerId, pageable);
        List<ReviewResponseDto> reviewResponseDtoList = reviews.stream().map(ReviewResponseDto::new).toList();

        Long count = getLikeCountByCustomer(customerId);

        return new PageImpl<ReviewResponseDto>(reviewResponseDtoList, pageable, count);
    }








    /* Util */

    // Customer 가 좋아요 한 갯수
    public Long getLikeCountByCustomer(Long customerId) {
        return reviewRepository.getLikeCountByCustomer(customerId);
    }

    public Review findReviewByIdAndCustomercheck(Long reviewId, Customer customer) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new ReviewException(ErrorType.NOT_FOUND_REVIEW)
        );
        if (!customer.getId().equals(review.getCustomer().getId())) {
            throw new ReviewException(ErrorType.INVALID_ORDER_CUSTOMER);
        }

        return review;
    }
}
