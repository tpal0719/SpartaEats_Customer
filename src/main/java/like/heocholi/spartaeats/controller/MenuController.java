package like.heocholi.spartaeats.controller;

import like.heocholi.spartaeats.dto.MenuResponseDto;
import like.heocholi.spartaeats.dto.ResponseMessage;
import like.heocholi.spartaeats.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    //메뉴 단건 조회
    @GetMapping("/stores/{storeId}/menus/{menuId}")
    public ResponseEntity<ResponseMessage> getMenu(@PathVariable Long storeId, @PathVariable Long menuId) {
        MenuResponseDto responseDto = menuService.getMenu(storeId,menuId);

        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseMessage.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("["+responseDto.getStoreName() + "]에 ["+ responseDto.getName()+"] 메뉴 조회가 완료되었습니다.")
                        .data(responseDto)
                        .build()
        );
    }

    //메뉴 전체 조회
    @GetMapping("/stores/{storeId}/menus")
    public ResponseEntity<ResponseMessage> getMenus(@PathVariable Long storeId) {
        List<MenuResponseDto> menuResponseDtoList = menuService.getMenus(storeId);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseMessage.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("["+menuResponseDtoList.get(0).getStoreName() +"]의 모든 메뉴 조회가 완료되었습니다.")
                        .data(menuResponseDtoList)
                        .build()
        );
    }


}
