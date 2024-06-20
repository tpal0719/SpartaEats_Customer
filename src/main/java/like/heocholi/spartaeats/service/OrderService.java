package like.heocholi.spartaeats.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import like.heocholi.spartaeats.dto.OrderListResponseDTO;
import like.heocholi.spartaeats.dto.OrderResponseDTO;
import like.heocholi.spartaeats.entity.Cart;
import like.heocholi.spartaeats.entity.Customer;
import like.heocholi.spartaeats.entity.Order;
import like.heocholi.spartaeats.entity.OrderMenu;
import like.heocholi.spartaeats.entity.Store;
import like.heocholi.spartaeats.exception.ContentNotFoundException;
import like.heocholi.spartaeats.exception.OrderException;
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
	
	/*
	 * 2. 주문 내역 조회
	 */
	public OrderListResponseDTO getOrders(Integer page, Customer customer) {
		Pageable pageable = createPageable(page);
		Page<Order> orderPage = orderRepository.findAllByCustomer(customer, pageable);
		
		checkValidatePage(page, orderPage);
		
		return new OrderListResponseDTO(page, orderPage);
	}
	
	
	/*
	 * 3. 주문 상세 정보 조회
	 */
	public OrderResponseDTO getOrderDetails(Long orderId, Customer customer) {
		Order order = getOrder(orderId);
		checkValidateUser(order, customer);
		
		return new OrderResponseDTO(order);
	}
	
	/*
	 * 4. 주문 취소하기
	 */
	@Transactional
	public Long cancelOrder(Long orderId, Customer customer) {
		Order order = getOrder(orderId);
		checkValidateUser(order, customer);
		
		order.cancelOrder();
		
		return orderId;
	}
	
	/*
	 * 주문 내역 조회
	 */
	private Order getOrder(Long orderId) {
		return orderRepository.findById(orderId).orElseThrow(() -> new ContentNotFoundException("주문 내역이 존재하지 않습니다."));
	}
	
	/*
	 * 사용자 유효성 검사
	 */
	private void checkValidateUser(Order order, Customer customer) {
		if (!order.getCustomer().getId().equals(customer.getId())) {
			throw new OrderException("본인의 주문 내역이 아닙니다.");
		}
	}
	
	/*
	 * 페이지네이션
	 */
	private Pageable createPageable(Integer page) {
		return PageRequest.of(page-1, 5, Sort.by("createdAt").descending());
	}
	
	/*
	 * 페이지 유효성 검사
	 */
	private static void checkValidatePage(Integer page, Page<Order> orderPage) {
		if (orderPage.getTotalElements() == 0) {
			throw new ContentNotFoundException("주문 내역이 없습니다.");
		}
		
		if (page > orderPage.getTotalPages() || page < 1) {
			throw new ContentNotFoundException("페이지가 존재하지 않습니다.");
		}
	}
}
