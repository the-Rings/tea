package rain.mocking.binarytea.runner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import rain.mocking.binarytea.model.MenuItem;
import rain.mocking.binarytea.service.MenuService;

import java.util.List;

@Component
@Order(2)
@Slf4j
@RequiredArgsConstructor
public class MenuPrinterRunner implements ApplicationRunner {
  private final MenuService menuService;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    log.info("再次加载菜单列表，应该不会访问数据库");
    List<MenuItem> list = menuService.getAllMenu();
    log.info("共有{}个饮品可选。", list.size());
    list.forEach(
        i ->
            log.info(
                "重新查询菜单项({})：{}",
                i.getId(),
                menuService.getByNameAndSize(i.getName(), i.getSize())));
  }
}
