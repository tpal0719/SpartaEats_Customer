package like.heocholi.spartaeats.controller;

import like.heocholi.spartaeats.entity.Menu;
import like.heocholi.spartaeats.service.MenuService;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MenuController {

    MenuService menuService;

    // C
    //메뉴 등록
    @GetMapping("/stores/{storeId}/menus")
    public void createMenu(@PathVariable int storeId, User user) {
        //return menuService.createMenu(storeId,user);
        menuService.createMenu(storeId,user);
    }

    // R
    //메뉴 단건 조회
    @GetMapping("/stores/{storeId}/menus/{menuId}")
    public Menu getMenu(@PathVariable int storeId, @PathVariable int menuId) {
        return menuService.getMenu();
    }

    //메뉴 전체 조회
    @GetMapping("/stores/{storeId}/menus")
    public List<Menu> getMenus(@PathVariable int storeId) {
        return menuService.getMenus(storeId);
    }



    // U
    //메뉴 수정
    @PutMapping("/menus/{menuId}")
    public void updateMenu(@PathVariable int menuId, User user) {
        //return menuService.updateMenu(menuId,user);
        menuService.updateMenu(menuId,user);
    }

    // D
    //메뉴 삭제
    @DeleteMapping("/menus/{menuId}")
    public void deleteMenu(@PathVariable int menuId, User user) {
        //return menuService.deleteMenu(menuId,user);
        menuService.deleteMenu(menuId,user);
    }
}
