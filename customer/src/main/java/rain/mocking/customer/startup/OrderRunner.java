package rain.mocking.customer.startup;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import rain.mocking.customer.integratoin.FeignOrderService;
import rain.mocking.customer.model.NewOrderForm;
import rain.mocking.customer.model.OrderVo;
import rain.mocking.customer.model.StatusForm;

import java.util.List;
import java.util.Objects;

@Slf4j
// @Order(5)
@Setter
// @Component
@RequiredArgsConstructor
public class OrderRunner implements ApplicationRunner {
    private final FeignOrderService feignOrderService;

  @Override
  public void run(ApplicationArguments args) {
    openfeignGetOrder();
  }

  protected void openfeignGetOrder() {
    List<OrderVo> orderVos = feignOrderService.listOrders();
    log.info("调用前订单的数量：{}", orderVos.size());
    NewOrderForm form = NewOrderForm.builder().itemIdList(List.of("2")).discount(90).build();
    ResponseEntity<OrderVo> response = feignOrderService.createNewOrder(form);
    log.info("HTTP Status: {}, Headers: {}", response.getStatusCode(), response.getHeaders());
    log.info("Body: {}", response.getBody());

    log.info("支付功能：简单修改订单的状态就相当于支付成功了");
    log.info("开始支付订单：{}", Objects.requireNonNull(response.getBody()).getId());
    StatusForm sf = StatusForm.builder().id(response.getBody().getId()).status("PAID").build();
    response = feignOrderService.modifyOrderStatus(sf);
    log.info("HTTP Status: {}, Headers: {}", response.getStatusCode(), response.getHeaders());
    log.info("Body: {}", response.getBody());
  }
}
