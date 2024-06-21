package like.heocholi.spartaeats.service;

import like.heocholi.spartaeats.dto.ReviewAddRequestDto;
import like.heocholi.spartaeats.dto.ReviewResponseDto;
import like.heocholi.spartaeats.dto.ReviewUpdateRequestDto;
import like.heocholi.spartaeats.entity.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {


    public List<ReviewResponseDto> getReviews(Long storeId, Customer customer) {
        return null;
    }

    public ReviewResponseDto getReview(Long storeId,Long reviewId, Customer customer) {
        return null;
    }

    public ReviewAddRequestDto addReview(Long orderId, Customer customer) {
        return null;
    }

    public ReviewUpdateRequestDto updateReview(Long reviewId, Customer customer) {
        return null;
    }

    public ReviewResponseDto deleteReview(Long reviewId, Customer customer) {
        return null;
    }
}
