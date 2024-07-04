package like.heocholi.spartaeats.controller;

import like.heocholi.spartaeats.constants.RestaurantType;
import like.heocholi.spartaeats.dto.*;
import like.heocholi.spartaeats.security.UserDetailsImpl;
import like.heocholi.spartaeats.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {
    private final StoreService storeService;

    // 음식점 상세 보기
    @GetMapping("/{storeId}")
    public ResponseEntity<ResponseMessage<StoreResponseDto>> readStore(@PathVariable Long storeId) {
        StoreResponseDto responseDto = storeService.readStore(storeId);

        ResponseMessage<StoreResponseDto> responseMessage = ResponseMessage.<StoreResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message(storeId + "번 가게 상세 정보를 성공적으로 불러왔습니다.")
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);

    }


    // 찜한 음식점만 조회 (최신순, 5개씩)
    @GetMapping("/pickstore/order/date")
    public Page<StoreResponseDto> getStoresUserLikedWithPage(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size){
        return storeService.getStoreCustomerPickWithPage(userDetails.getCustomer().getId(), page-1, size);
    }

    @GetMapping("/pickstore/order/managerid")
    public Page<StoreResponseDto> getStoreCustomerPickWithPageOrderByManagerId(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {
        return storeService.getStoreCustomerPickWithPageOrderByManagerId(userDetails.getCustomer().getId(), page - 1, size);
    }
    //음식점 리스트 보기
    //주문이 많은 순서대로
    @GetMapping
    public ResponseEntity<ResponseMessage<StorePageResponseDto>> getStorePage (
            @RequestParam String type,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
        StorePageResponseDto storePageResponseDto = storeService.getStorePageByType(type, page);

        ResponseMessage<StorePageResponseDto> responseMessage = ResponseMessage.<StorePageResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message(page + "번 페이지 가게 리스트를 성공적으로 불러왔습니다.")
                .data(storePageResponseDto)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    // 가게 찜 등록 || 취소
    @PostMapping("/{storeId}/pick")
    public ResponseEntity<ResponseMessage<PickStoreResponseDto>> managePicks
            (@PathVariable Long storeId,
             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PickStoreResponseDto responseDto = storeService.managePicks(storeId, userDetails.getCustomer());

        ResponseMessage<PickStoreResponseDto> responseMessage = ResponseMessage.<PickStoreResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message(storeId + "번 가게 찜 등록 / 취소가 완료되었습니다. ")
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }


}
