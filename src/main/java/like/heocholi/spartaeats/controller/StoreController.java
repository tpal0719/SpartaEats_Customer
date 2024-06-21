package like.heocholi.spartaeats.controller;

import jakarta.validation.Valid;
import like.heocholi.spartaeats.dto.*;
import like.heocholi.spartaeats.security.UserDetailsImpl;
import like.heocholi.spartaeats.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    //음식점 리스트 보기
    //주문이 많은 순서대로
    @GetMapping
    public ResponseEntity<ResponseMessage<StorePageResponseDto>> getStorePage (
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
        StorePageResponseDto storePageResponseDto = storeService.getStorePage(page);

        ResponseMessage<StorePageResponseDto> responseMessage = ResponseMessage.<StorePageResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message(page + "번 페이지 가게 리스트를 성공적으로 불러왔습니다.")
                .data(storePageResponseDto)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
}
