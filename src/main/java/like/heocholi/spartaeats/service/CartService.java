package like.heocholi.spartaeats.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import like.heocholi.spartaeats.dto.CartRequestDTO;
import like.heocholi.spartaeats.entity.Cart;
import like.heocholi.spartaeats.entity.Customer;
import like.heocholi.spartaeats.entity.Menu;
import like.heocholi.spartaeats.exception.CartException;
import like.heocholi.spartaeats.repository.CartRepository;
import like.heocholi.spartaeats.repository.MenuRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {
	private final CartRepository cartRepository;
	private final MenuRepository menuRepository;
	
	/*
	 * 1. 장바구니에 메뉴 추가
	 */
	@Transactional
	public Long addCart(CartRequestDTO cartRequestDTO, Customer customer) {
		Long menuId = cartRequestDTO.getMenuId();
		Long storeId = cartRequestDTO.getStoreId();
		
		Menu menu = getMenu(menuId);
		
		List<Cart> cartList = getCartList(customer);
		
		for (Cart cart : cartList) {
			if (!Objects.equals(storeId, cart.getStore().getId())) {
				throw new CartException("장바구니에는 같은 가게의 메뉴만 담을 수 있습니다.");
			}
			
			if (Objects.equals(menuId, cart.getMenu().getId())) {
				throw new CartException("이미 장바구니에 담긴 메뉴입니다.");
			}
		}
		
		Cart cart = Cart.builder()
			.menu(menu)
			.store(menu.getStore())
			.customer(customer)
			.quantity(cartRequestDTO.getQuantity())
			.build();
		
		cartRepository.save(cart);
		
		return menuId;
	}
	
	/*
	 * 메뉴 조회
	 */
	private Menu getMenu(Long menuId) {
		return menuRepository.findById(menuId).orElseThrow(() -> new CartException("해당 메뉴가 없습니다."));
	}
	
	/*
	 * 장바구니 조회
	 */
	public List<Cart> getCartList(Customer customer) {
		List<Cart> cartList = cartRepository.findByCustomer(customer);
		
		return cartList;
	}
}
