package rain.mocking.binarytea.dubbo;

import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import rain.mocking.binarytea.model.MenuItem;
import rain.mocking.binarytea.service.MenuService;
import rain.mocking.dubbo.IMenuDubboService;
import rain.mocking.dubbo.MenuDubboItem;

import java.util.List;
import java.util.stream.Collectors;

@DubboService
@RequiredArgsConstructor
public class MenuDubboServiceImpl implements IMenuDubboService {
  private final MenuService menuService;

  @Override
  public List<MenuDubboItem> getAllMenu() {
    return menuService.getAllMenu().stream().map(this::convert).collect(Collectors.toList());
  }

  private MenuDubboItem convert(MenuItem origin) {
    MenuDubboItem item = new MenuDubboItem();
    BeanUtils.copyProperties(origin, item);
    item.size = origin.getSize().name();
    item.price = origin.getPrice();
    return item;
  }
}
