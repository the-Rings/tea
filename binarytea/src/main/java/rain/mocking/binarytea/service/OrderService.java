package rain.mocking.binarytea.service;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rain.mocking.binarytea.config.BinaryTeaProperties;
import rain.mocking.binarytea.model.Amount;
import rain.mocking.binarytea.model.Menu;
import rain.mocking.binarytea.model.Order;
import rain.mocking.binarytea.model.OrderStatus;
import rain.mocking.binarytea.repository.OrderRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
  private final OrderRepository orderRepository;
  private final BinaryTeaProperties binaryTeaProperties;

  public List<Order> getAllOrders() {
    return orderRepository.findAll();
  }

  @Transactional
  public Order createOrder(List<Menu> itemList, int discount) {
    int newDiscount = discount == 100 ? binaryTeaProperties.getDiscount() : discount;
    long total = itemList.stream().mapToLong(Menu::getPrice).sum();
    Long pay = total * (newDiscount / 10L);

    Amount amount =
        Amount.builder().discount(newDiscount).totalAmount(total).payAmount(pay).build();
    Order order =
        Order.builder().amount(amount).status(OrderStatus.ORDERED).items(itemList).build();
    return orderRepository.save(order);
  }

  @Transactional
  public Order modifyOrderStatus(Long id, OrderStatus status) {
    Optional<Order> order = orderRepository.findById(id);
    if (order.isEmpty()) {
      log.warn("订单{}不存在", id);
      return null;
    }
    OrderStatus old = order.get().getStatus();
    if (status.getIndex() != old.getIndex() + 1) {
      log.warn("订单{}无法从状态{}改为{}", id, old, status);
      return null;
    }
    order.get().setStatus(status);
    return orderRepository.save(order.get());
  }

  public int modifyOrdersState(List<Long> idList, OrderStatus oldState, OrderStatus newState) {
    List<Order> orders = orderRepository.findByStatusEqualsAndIdInOrderById(oldState, idList);
    orders.forEach(o -> o.setStatus(newState));
    return orderRepository.saveAll(orders).size();
  }

  public Optional<Order> queryOrder(Long id) {
    return orderRepository.findById(id);
  }
}
