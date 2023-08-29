package rain.mocking.binarytea.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import rain.mocking.binarytea.controller.request.NewOrderForm;
import rain.mocking.binarytea.model.MenuItem;
import rain.mocking.binarytea.model.Order;
import rain.mocking.binarytea.model.OrderStatus;
import rain.mocking.binarytea.service.MenuService;
import rain.mocking.binarytea.service.OrderService;

import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order")
@Slf4j
@RequiredArgsConstructor
public class OrderController {
  private final OrderService orderService;
  private final MenuService menuService;

  @ModelAttribute("items")
  public List<MenuItem> items() {
    return menuService.getAllMenu();
  }

  @GetMapping
  public ModelAndView orderPage() {
    return new ModelAndView("order")
        .addObject(new NewOrderForm())
        .addObject("orders", orderService.getAllOrders());
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public List<Order> listOrders() {
    return orderService.getAllOrders();
  }

  @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public String createNewOrder(@Valid NewOrderForm form, BindingResult result, ModelMap modelMap) {
    if (result.hasErrors()) {
      modelMap.addAttribute("orders", orderService.getAllOrders());
      return "order";
    }
    createOrder(form);
    modelMap.addAttribute("orders", orderService.getAllOrders());
    return "order";
  }

  @ResponseBody
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Order> createNewOrder(
      @RequestBody @Valid NewOrderForm form, BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.badRequest().body(null);
    }
    Order order = createOrder(form);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri();
    return ResponseEntity.created(uri).body(order);
  }

  @PutMapping
  public String modifyOrdersToPaid(@RequestParam("id") String id, ModelMap modelMap) {
    int successCount = 0;
    if (StringUtils.isNotBlank(id)) {
      List<Long> orderIdList =
          Arrays.stream(id.split(","))
              .map(s -> NumberUtils.toLong(s, -1))
              .filter(l -> l > 0)
              .collect(Collectors.toList());
      successCount =
          orderService.modifyOrdersState(orderIdList, OrderStatus.ORDERED, OrderStatus.PAID);
    }
    modelMap.addAttribute(new NewOrderForm());
    modelMap.addAttribute("success_count", successCount);
    modelMap.addAttribute("orders", orderService.getAllOrders());
    return "order";
  }

  private Order createOrder(NewOrderForm form) {
    List<MenuItem> itemList =
        form.getItemIdList().stream()
            .map(NumberUtils::toLong)
            .collect(Collectors.collectingAndThen(Collectors.toList(), menuService::getByIdList));
    Order order = orderService.createOrder(itemList, form.getDiscount());
    log.info("创建新订单，Order={}", order);
    return order;
  }
}
