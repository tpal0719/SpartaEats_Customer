package like.heocholi.spartaeats.controller;

import jakarta.validation.Valid;
import like.heocholi.spartaeats.dto.ResponseMessage;
import like.heocholi.spartaeats.dto.SignupRequestDto;
import like.heocholi.spartaeats.dto.WithdrawRequestDto;
import like.heocholi.spartaeats.security.UserDetailsImpl;
import like.heocholi.spartaeats.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequestDto requestDto){

        String resultMessage = customerService.signup(requestDto);

        return ResponseEntity.ok(resultMessage);
    }

    @PutMapping("/withdraw")
    public ResponseEntity<?> withdrawCustomer(@RequestBody WithdrawRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String userId = userDetails.getUsername();
        String withdrawnUserId = customerService.withdrawCustomer(requestDto, userId);

        ResponseMessage<String> responseMessage = ResponseMessage.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("회원 탈퇴가 완료되었습니다.")
                .data(withdrawnUserId)
                .build();

        return ResponseEntity.ok().body(responseMessage);
    }
}
