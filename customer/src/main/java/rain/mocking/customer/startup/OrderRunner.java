package rain.mocking.customer.startup;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import rain.mocking.customer.integratoin.OrderService;
import rain.mocking.customer.model.NewOrderForm;
import rain.mocking.customer.model.StatusForm;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@Slf4j
@Order(5)
@Setter
@Component
@RequiredArgsConstructor
public class OrderRunner implements ApplicationRunner {
  private final RestTemplate restTemplate;
  private final OrderService orderService;

  @Value("${binarytea.url}")
  private String binarytea;

  @Override
  public void run(ApplicationArguments args) {
    restTemplateGetOrder();
    openfeignGetOrder();
  }

  protected void openfeignGetOrder() {
    List<rain.mocking.customer.model.Order> orders = orderService.listOrders();
    log.info("调用前订单的数量：{}", orders.size());
    NewOrderForm form = NewOrderForm.builder().itemIdList(List.of("2")).discount(90).build();
    ResponseEntity<rain.mocking.customer.model.Order> response = orderService.createNewOrder(form);
    log.info("HTTP Status: {}, Headers: {}", response.getStatusCode(), response.getHeaders());
    log.info("Body: {}", response.getBody());

    log.info("支付功能：简单修改订单的状态就相当于支付成功了");
    log.info("开始支付订单：{}", Objects.requireNonNull(response.getBody()).getId());
    StatusForm sf = StatusForm.builder().id(response.getBody().getId()).status("PAID").build();
    response = orderService.modifyOrderStatus(sf);
    log.info("HTTP Status: {}, Headers: {}", response.getStatusCode(), response.getHeaders());
    log.info("Body: {}", response.getBody());
  }

  /**
   * 使用原始的 RestTemplate 请求 binarytea 服务
   *
   * @return ResponseEntity
   */
  @Deprecated
  protected ResponseEntity<String> restTemplateGetOrder() {
    NewOrderForm form = NewOrderForm.builder().itemIdList(List.of("1")).discount(90).build();
    URI uri = UriComponentsBuilder.fromUriString(binarytea + "/order").build().toUri();
    RequestEntity<NewOrderForm> request =
        RequestEntity.post(uri).contentType(MediaType.APPLICATION_JSON).body(form);
    ResponseEntity<String> response =
        restTemplate.postForEntity(binarytea + "/order", request, String.class);
    log.info("HTTP Status: {}, Headers: ", response.getStatusCode());
    response.getHeaders().forEach((key, value) -> log.info("{}: {}", key, value));
    log.info("Body: {}", response.getBody());
    return response;
  }
}
