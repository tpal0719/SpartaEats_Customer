package like.heocholi.spartaeats.service;

import like.heocholi.spartaeats.constants.ErrorType;
import like.heocholi.spartaeats.dto.ReviewAddRequestDto;
import like.heocholi.spartaeats.dto.ReviewResponseDto;
import like.heocholi.spartaeats.dto.ReviewUpdateRequestDto;
import like.heocholi.spartaeats.entity.*;
import like.heocholi.spartaeats.exception.OrderException;
import like.heocholi.spartaeats.exception.ReviewException;
import like.heocholi.spartaeats.repository.OrderMenuRepository;
import like.heocholi.spartaeats.repository.OrderRepository;
import like.heocholi.spartaeats.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;

    public List<ReviewResponseDto> getReviews(Long storeId, Customer customer) {
        return null;
    }

    public ReviewResponseDto getReview(Long storeId, Long reviewId, Customer customer) {
        return null;
    }


    // 리뷰 작성
    public ReviewResponseDto addReview(Long orderId,ReviewAddRequestDto reviewAddRequestDto,Customer customer) {

        Order order = orderRepository.findById(orderId).orElseThrow(
                ()->  new ReviewException(ErrorType.NOT_FOUND_ORDER)
        );

        if(!customer.equals(order.getCustomer())) {
            throw new ReviewException(ErrorType.INVALID_ORDER_CUSTOMER);
        }

        Review review = Review.builder()
                .order(order)
                .store(order.getStore())
                .customer(order.getCustomer())
                .contents(reviewAddRequestDto.getContent())
                .build();

        reviewRepository.save(review);

        return new ReviewResponseDto(review);
    }

    public ReviewResponseDto updateReview(Long reviewId,ReviewUpdateRequestDto requestDto, Customer customer) {

        Review review = reviewRepository.findById(reviewId).orElseThrow(
                ()-> new ReviewException(ErrorType.NOT_FOUND_REVIEW)
        );
        if(!customer.equals(review.getCustomer())) {
            throw new ReviewException(ErrorType.INVALID_ORDER_CUSTOMER);
        }

        review.update(requestDto.getContents());

        return new ReviewResponseDto(review);
    }

    public ReviewResponseDto deleteReview(Long reviewId, Customer customer) {
        return null;
    }
}
