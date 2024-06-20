package like.heocholi.spartaeats.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import like.heocholi.spartaeats.dto.OrderResponseDTO;
import like.heocholi.spartaeats.entity.Cart;
import like.heocholi.spartaeats.entity.Customer;
import like.heocholi.spartaeats.entity.Order;
import like.heocholi.spartaeats.entity.OrderMenu;
import like.heocholi.spartaeats.entity.Store;
import like.heocholi.spartaeats.exception.ContentNotFoundException;
import like.heocholi.spartaeats.repository.OrderMenuRepository;
import like.heocholi.spartaeats.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	private final CartService cartService;
	private final OrderMenuRepository orderMenuRepository;
	
	/*
	 * 1. 주문하기
	 */
	@Transactional
	public OrderResponseDTO saveOrder(Customer customer) {
		List<Cart> cartList = cartService.getCartList(customer);
		
		if (cartList.isEmpty()) {
			throw new ContentNotFoundException("장바구니에 담긴 메뉴가 없습니다.");
		}
		
		Store store = cartList.get(0).getStore();
		int totalPrice = cartList.stream().mapToInt(cart -> cart.getMenu().getPrice()).sum();
		
		Order order = new Order(store, customer);
		Order saveOrder = orderRepository.save(order);
		
		List<OrderMenu> orderMenuList = cartList.stream()
			.map(cart -> OrderMenu.builder()
				.order(saveOrder)
				.menu(cart.getMenu())
				.count(cart.getQuantity())
				.price(cart.getMenu().getPrice() * cart.getQuantity())
				.build())
			.toList();
		
		orderMenuRepository.saveAll(orderMenuList);
		
		saveOrder.updateOrder(orderMenuList, totalPrice);
		
		cartService.deleteAllCart(customer);
		
		return new OrderResponseDTO(saveOrder);
	}
}
