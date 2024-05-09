package rain.mocking.customer.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rain.mocking.customer.integratoin.FeignMenuService;
import rain.mocking.customer.integratoin.FeignOrderService;
import rain.mocking.customer.model.MenuVo;
import rain.mocking.customer.model.NewOrderForm;
import rain.mocking.customer.model.OrderVo;
import rain.mocking.customer.model.StatusForm;

/**
 * @author mao
 * @date 2024/4/21 18:01
 */
@RestController
@RequestMapping("/customer")
@Slf4j
@RequiredArgsConstructor
public class CustomerController {
  private final FeignOrderService feignOrderService;
  private final FeignMenuService feignMenuService;

  @GetMapping("/orders")
  public ResponseEntity<List<OrderVo>> queryCustomerOrders() {
    List<OrderVo> orderVos = feignOrderService.listOrders();
    return ResponseEntity.ok(orderVos);
  }

  @GetMapping("/items")
  public ResponseEntity<List<MenuVo>> queryItems() {
    List<MenuVo> items = feignMenuService.listMenus();
    return ResponseEntity.ok(items);
  }

  @PostMapping("/order")
  public ResponseEntity<OrderVo> createNewOrder(@RequestParam("id") String items, @RequestParam("dis") Integer discount) {
    if (StringUtils.isNoneBlank(items)) {
      NewOrderForm form = new NewOrderForm();
      form.setItemIdList(Arrays.stream(items.split(",")).collect(Collectors.toList()));
      if (discount == null) {
        discount = 95;
      }
      form.setDiscount(discount);
      return feignOrderService.createNewOrder(form);
    }
    return ResponseEntity.badRequest().body(null);
  }

  @PatchMapping("/pay")
  public ResponseEntity<OrderVo> paySingleOrder(@RequestParam("id") Long id) {
    StatusForm sf = StatusForm.builder().id(id).status("PAID").build();
    return feignOrderService.modifyOrderStatus(sf);
  }

  @PatchMapping("/take")
  public ResponseEntity<OrderVo> takeTea(@RequestParam("id") Long id) {
    StatusForm sf = StatusForm.builder().id(id).status("TAKEN").build();
    return feignOrderService.modifyOrderStatus(sf);
  }
}
