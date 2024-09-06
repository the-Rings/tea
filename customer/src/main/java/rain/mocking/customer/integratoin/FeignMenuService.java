package rain.mocking.customer.integratoin;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import rain.mocking.customer.model.MenuVo;

/**
 * @author mao
 * @date 2024/4/21 19:53
 */
@FeignClient(contextId = "menuService", name = "binarytea", path = "/menu")
public interface FeignMenuService {
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  List<MenuVo> listMenus();
}
