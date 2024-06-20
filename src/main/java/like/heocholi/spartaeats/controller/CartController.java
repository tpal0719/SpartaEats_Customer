package like.heocholi.spartaeats.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import like.heocholi.spartaeats.dto.CartRequestDTO;
import like.heocholi.spartaeats.dto.ResponseMessage;
import like.heocholi.spartaeats.security.UserDetailsImpl;
import like.heocholi.spartaeats.service.CartService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {
	private final CartService cartService;
	
	// 장바구니에 메뉴 추가
	@PostMapping
	public ResponseEntity<ResponseMessage<Long>> addCart(@Valid @RequestBody CartRequestDTO cartRequestDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		Long menuId = cartService.addCart(cartRequestDTO, userDetails.getCustomer());
		
		ResponseMessage<Long> responseMessage = ResponseMessage.<Long>builder()
			.statusCode(HttpStatus.CREATED.value())
			.message("장바구니에 메뉴가 추가되었습니다.")
			.data(menuId)
			.build();
		
		return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
	}
}
