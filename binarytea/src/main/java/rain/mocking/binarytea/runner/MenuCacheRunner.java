package rain.mocking.binarytea.runner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import rain.mocking.binarytea.model.MenuItem;
import rain.mocking.binarytea.model.Size;
import rain.mocking.binarytea.service.MenuService;

import java.util.List;

@Component
@Order(1)
@Slf4j
@RequiredArgsConstructor
public class MenuCacheRunner implements ApplicationRunner {
  private final MenuService menuService;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    log.info("从数据库加载菜单列表，后续应该就在缓存里了");
    List<MenuItem> list = menuService.getAllMenu();
    log.info("共取得{}个条目。", list.size());
    menuService
        .getByNameAndSize("Java咖啡", Size.MEDIUM)
        .ifPresent(m -> log.info("加载中杯Java咖啡，放入缓存，ID={}", m.getId()));
  }
}
