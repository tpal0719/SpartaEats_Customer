package like.heocholi.spartaeats.controller;

import jakarta.validation.Valid;
import like.heocholi.spartaeats.dto.*;
import like.heocholi.spartaeats.security.UserDetailsImpl;
import like.heocholi.spartaeats.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import like.heocholi.spartaeats.dto.ResponseMessage;
import like.heocholi.spartaeats.dto.SignupRequestDto;
import like.heocholi.spartaeats.dto.SignupResponseDto;
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

    @Autowired
    private final CustomerService customerService;

    // 회원가입 API
    // request 회원가입 요청 DTO (SignupRequestDto)
    // ResponseEntity<String> 회원가입 결과 메시지
    @PostMapping
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequestDto requestDto){
        SignupResponseDto responseDto = customerService.signup(requestDto);
        ResponseMessage<SignupResponseDto> responseMessage = ResponseMessage.<SignupResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("회원가입 성공")
                .data(responseDto)
                .build();

        return ResponseEntity.ok().body(responseMessage);
    }

    @PutMapping("/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal UserDetailsImpl userDetails){
        String userId = customerService.logout(userDetails.getUsername());
        ResponseMessage<String> responseMessage = ResponseMessage.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("로그아웃 성공")
                .data(userId)
                .build();

        return ResponseEntity.ok().body(responseMessage);
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

    @GetMapping
    public ResponseEntity<ResponseMessage<CustomerResponseDTO>> getCustomerInfo (@AuthenticationPrincipal UserDetailsImpl userDetails) {
        CustomerResponseDTO responseDTO =  customerService.getCustomerInfo(userDetails.getCustomer());

        ResponseMessage<CustomerResponseDTO> responseMessage = ResponseMessage.<CustomerResponseDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("프로필 조회가 완료되었습니다.")
                .data(responseDTO)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    // 비밀번호 업데이트 API
    // customerId 사용자 ID
    // request 요청 DTO (PasswordRequest)
    // ResponseEntity<String> 업데이트 결과 메시지
    @PutMapping("/password")
    public ResponseEntity<ResponseMessage<String>> updatePassword(@RequestBody @Valid PasswordRequestDTO request,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String customerId = customerService.updatePassword(request, userDetails.getCustomer());

        ResponseMessage<String> responseMessage = ResponseMessage.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("비밀번호 변경이 완료되었습니다.")
                .data(customerId)
                .build();


        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    //프로필 업데이트 API
    // customerId 사용자 ID
    // request 요청 DTO (ProfileRequest)
    // ResponseEntity<String> 업데이트 결과 메시지
    @PutMapping
    public ResponseEntity<ResponseMessage<ProfileResponseDTO>> updateProfile(@RequestBody @Valid ProfileRequestDTO request,
                                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ProfileResponseDTO responseDTO = customerService.updateProfile(request, userDetails.getCustomer());

        ResponseMessage<ProfileResponseDTO> responseMessage = ResponseMessage.<ProfileResponseDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("프로필 업데이트가 완료되었습니다.")
                .data(responseDTO)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
}
