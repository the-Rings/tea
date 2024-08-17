package rain.mocking.binarytea.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rain.mocking.binarytea.controller.request.NewOrderForm;
import rain.mocking.binarytea.controller.request.StatusForm;
import rain.mocking.binarytea.model.Menu;
import rain.mocking.binarytea.model.Order;
import rain.mocking.binarytea.model.OrderStatus;
import rain.mocking.binarytea.service.MenuService;
import rain.mocking.binarytea.service.OrderService;

@RestController
@RequestMapping("/order")
@Slf4j
@RequiredArgsConstructor
public class OrderController {
  private final OrderService orderService;
  private final MenuService menuService;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Order> listOrders() {
    return orderService.getAllOrders();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Order> queryOneOrder(@PathVariable("id") Long id) {
    Optional<Order> result = orderService.queryOrder(id);
    return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Order> createNewOrder(
      @RequestBody @Valid NewOrderForm form, BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.badRequest().body(null);
    }
    List<Menu> itemList =
        form.getItemIdList().stream()
            .map(NumberUtils::toLong)
            .collect(Collectors.collectingAndThen(Collectors.toList(), menuService::getByIdList));
    Order order = orderService.createOrder(itemList, form.getDiscount());
    log.info("创建新订单，Order={}", order);
    return ResponseEntity.ok(order);
  }

  @PutMapping
  public ResponseEntity<Integer> modifyOrdersToPaid(@RequestParam("id") String id) {
    int successCount = 0;
    if (StringUtils.isNotBlank(id)) {
      List<Long> orderIdList =
          Arrays.stream(id.split(","))
              .map(s -> NumberUtils.toLong(s, -1))
              .filter(l -> l > 0)
              .collect(Collectors.toList());
      successCount =
          orderService.modifyOrdersState(orderIdList, OrderStatus.ORDERED, OrderStatus.PAID);
      return ResponseEntity.ok(successCount);
    }
    return ResponseEntity.badRequest().body(null);
  }

  @PutMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Order> modifyOrderStatus(
      @RequestBody @Valid StatusForm form, BindingResult result) {
    log.info("BindingResult用来封装@Valid校验的错误信息，所以@FeignClient不需要@Valid");
    if ("CIRCUIT_BREAKER".equals(form.getStatus())) {
      throw new RuntimeException("测试断路器");
    }
    if (result.hasErrors()) {
      return ResponseEntity.badRequest().body(null);
    }
    log.info("计划ID={}的订单状态更新为{}", form.getId(), form.getStatus());
    OrderStatus status = OrderStatus.valueOf(form.getStatus());
    Order order = orderService.modifyOrderStatus(form.getId(), status);
    return order == null ? ResponseEntity.badRequest().body(null) : ResponseEntity.ok(order);
  }

}
