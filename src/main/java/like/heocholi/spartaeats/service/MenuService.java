package like.heocholi.spartaeats.service;

import like.heocholi.spartaeats.entity.Menu;
import like.heocholi.spartaeats.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository postRepository;

    public Menu getMenu() {

        return null;
    }

    public List<Menu> getMenus(int storeId) {

        return null;
    }

}
