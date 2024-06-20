package like.heocholi.spartaeats.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import like.heocholi.spartaeats.dto.OrderResponseDTO;
import like.heocholi.spartaeats.dto.ResponseMessage;
import like.heocholi.spartaeats.security.UserDetailsImpl;
import like.heocholi.spartaeats.service.OrderService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
	private final OrderService orderService;
	
	// 주문 추가
	@PostMapping
	public ResponseEntity<ResponseMessage<OrderResponseDTO>> addOrder(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		OrderResponseDTO orderResponseDTO = orderService.saveOrder(userDetails.getCustomer());
		
		ResponseMessage<OrderResponseDTO> responseMessage = ResponseMessage.<OrderResponseDTO>builder()
			.statusCode(HttpStatus.CREATED.value())
			.message("주문이 완료되었습니다.")
			.data(orderResponseDTO)
			.build();
		
		return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
	}
	
}
