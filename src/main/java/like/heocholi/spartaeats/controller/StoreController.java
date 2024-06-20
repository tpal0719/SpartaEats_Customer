package like.heocholi.spartaeats.controller;

import jakarta.validation.Valid;
import like.heocholi.spartaeats.dto.ResponseMessage;
import like.heocholi.spartaeats.dto.StoreRequestDto;
import like.heocholi.spartaeats.dto.StoreResponseDto;
import like.heocholi.spartaeats.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
        return ResponseEntity.ok().body(
                ResponseMessage.<StoreResponseDto>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("가게 정보를 성공적으로 불러왔습니다.")
                        .data(responseDto)
                        .build()
        );
    }

    //음식점 리스트 보기
    //주문이 많은 순서대로
//    @GetMapping
//    public ResponseEntity<Page<StoreResponseDto>> getStorePage(@RequestParam int page) {
//        Page<StoreResponseDto> storePage = storeService.getStorePage(page, 5); // 한 페이지당 5개의 가게
//        return new ResponseEntity<>(storePage, HttpStatus.OK);
//    }
}
