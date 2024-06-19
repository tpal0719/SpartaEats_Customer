package like.heocholi.spartaeats.controller;

import jakarta.validation.Valid;
import like.heocholi.spartaeats.dto.SignupRequestDto;
import like.heocholi.spartaeats.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
