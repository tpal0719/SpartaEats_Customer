package like.heocholi.spartaeats.controller;

import like.heocholi.spartaeats.dto.ResponseMessage;
import like.heocholi.spartaeats.entity.Menu;
import like.heocholi.spartaeats.service.MenuService;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MenuController {

    MenuService menuService;

    // R
    //메뉴 단건 조회
    @GetMapping("/stores/{storeId}/menus/{menuId}")
    public ResponseEntity<ResponseMessage> getMenu(@PathVariable int storeId, @PathVariable int menuId) {
        return menuService.getMenu(storeId,menuId);
    }

    //메뉴 전체 조회
    @GetMapping("/stores/{storeId}/menus")
    public ResponseEntity<ResponseMessage> getMenus(@PathVariable int storeId) {
        return menuService.getMenus(storeId);
    }


}
