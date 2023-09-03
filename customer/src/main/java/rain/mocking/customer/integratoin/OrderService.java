package rain.mocking.customer.integratoin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import rain.mocking.customer.model.NewOrderForm;
import rain.mocking.customer.model.Order;
import rain.mocking.customer.model.StatusForm;

import java.util.List;

/**
 * @author mao
 * @date 2023/8/27
 */
@FeignClient(contextId = "orderService", name = "binarytea", path = "/order")
public interface OrderService {
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  List<Order> listOrders();

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Order> createNewOrder(@RequestBody NewOrderForm form);

  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Order> modifyOrderStatus(@RequestBody StatusForm form);
}
