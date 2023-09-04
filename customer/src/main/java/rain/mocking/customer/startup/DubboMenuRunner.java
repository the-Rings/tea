package rain.mocking.customer.startup;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import rain.mocking.dubbo.IMenuDubboService;
import rain.mocking.dubbo.MenuDubboItem;

import java.util.List;

/**
 * @author mao
 * @date 2023/9/4
 */
@Component
@Slf4j
@Order(3)
public class DubboMenuRunner implements ApplicationRunner {
  @DubboReference
  private IMenuDubboService menuDubboService;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    List<MenuDubboItem> items = menuDubboService.getAllMenu();
    log.info("通过Dubbo接口获得了{}个菜单项", items.size());
  }
}
