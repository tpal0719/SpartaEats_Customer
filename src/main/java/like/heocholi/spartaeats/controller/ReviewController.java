package like.heocholi.spartaeats.controller;

import like.heocholi.spartaeats.dto.*;
import like.heocholi.spartaeats.security.UserDetailsImpl;
import like.heocholi.spartaeats.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ReviewController {

    private final ReviewService reviewService;

    //리뷰 조회
    @GetMapping("/stores/{storeId}/reviews")
    public ResponseEntity<ResponseMessage<List<ReviewResponseDto>>> getReview(@PathVariable Long storeId,
                                                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<ReviewResponseDto> reviewList = reviewService.getReviews(storeId, userDetails.getCustomer());

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/stores/{storeId}/reviews/{reviewId}")
    public ResponseEntity<ResponseMessage<List<ReviewResponseDto>>> getReview(@PathVariable Long storeId,
                                                                              @PathVariable Long reviewId,
                                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {

        ReviewResponseDto requestDto = reviewService.getReview(storeId,reviewId,userDetails.getCustomer());

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    //리뷰 등록
    @PostMapping("/orders/{orderId}/reviews")
    public ResponseEntity<ResponseMessage<ReviewAddRequestDto>> addReview(@PathVariable Long orderId,
                                                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {

        ReviewAddRequestDto requestDto = reviewService.addReview(orderId,userDetails.getCustomer());

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    //리뷰 수정
    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<ResponseMessage<ReviewUpdateRequestDto>> updateReview(@PathVariable Long reviewId,
                                                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {

        ReviewUpdateRequestDto requestDto = reviewService.updateReview(reviewId,userDetails.getCustomer());

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }


    //리뷰 삭제
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<ResponseMessage<ReviewResponseDto>> deleteReview(@PathVariable Long reviewId,
                                                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {

        ReviewResponseDto requestDto = reviewService.deleteReview(reviewId,userDetails.getCustomer());

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }


}
