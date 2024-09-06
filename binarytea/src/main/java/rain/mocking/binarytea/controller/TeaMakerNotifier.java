package rain.mocking.binarytea.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import rain.mocking.binarytea.integration.TeaMakerClient;
import rain.mocking.binarytea.integration.TeaMakerResult;
import rain.mocking.binarytea.model.Order;
import rain.mocking.binarytea.model.OrderStatus;
import rain.mocking.binarytea.repository.OrderRepository;

@Slf4j
// @Component
@RequiredArgsConstructor
public class TeaMakerNotifier {
  private final OrderRepository orderRepository;
  private final TeaMakerClient teaMakerClient;

  @Scheduled(fixedDelay = 2000)
  public void notifyTeaMaker() {
    // 没考虑并发执行的问题
    List<Order> orders = orderRepository.findByStatusEquals(OrderStatus.PAID);
    for (Order o : orders) {
      try {
        notifyOneOrder(o);
      } catch (Exception e) {
        log.error("通知处理订单失败", e);
      }
    }
  }

  private void notifyOneOrder(Order o) {
    TeaMakerResult result = teaMakerClient.makeTea(o.getId());
    if (result == null || !result.isFinish()) {
      return;
    }
    o.setMakerId(result.getTeaMakerId());
    o.setStatus(OrderStatus.FINISHED);
    orderRepository.save(o);
  }
}
