package like.heocholi.spartaeats.service;

import like.heocholi.spartaeats.dto.ResponseMessage;
import like.heocholi.spartaeats.entity.Menu;
import like.heocholi.spartaeats.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    public Menu getMenu() {

        return null;
    }

    public ResponseEntity<ResponseMessage> getMenus(int storeId) {

        List<Menu> menus = menuRepository.findAllByStoreId(storeId).orElse(null);

        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseMessage.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("게시물 조회가 완료 되었습니다.")
                        .data(menus)
                        .build()
        );
    }

}
