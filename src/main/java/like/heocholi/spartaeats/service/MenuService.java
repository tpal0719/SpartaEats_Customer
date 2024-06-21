package like.heocholi.spartaeats.service;

import like.heocholi.spartaeats.constants.ErrorType;
import like.heocholi.spartaeats.dto.MenuResponseDto;
import like.heocholi.spartaeats.entity.Menu;
import like.heocholi.spartaeats.entity.Store;
import like.heocholi.spartaeats.exception.MenuException;
import like.heocholi.spartaeats.repository.MenuRepository;
import like.heocholi.spartaeats.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    public MenuResponseDto getMenu(Long storeId, Long menuId) {
        Store store = findStoreById(storeId);
        Menu menu = menuRepository.findByStoreIdAndId(storeId,menuId).orElseThrow(() -> new MenuException(ErrorType.NOT_FOUND_MENU));

        return new MenuResponseDto(menu);
    }

    public List<MenuResponseDto> getMenus(Long storeId) {
        Store store = findStoreById(storeId);
        List<MenuResponseDto> menuList = menuRepository.findAllByStoreId(storeId).stream().map(MenuResponseDto::new).toList();

        if(menuList.isEmpty()){
            throw new MenuException(ErrorType.NOT_FOUND_MENUS);
        }

       return menuList;
    }




    public Store findStoreById(Long storeId) {
        Store store =storeRepository.findById(storeId).orElseThrow(() -> new MenuException(ErrorType.NOT_FOUND_STORE));

        return store;
    }


}
