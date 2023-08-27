package rain.mocking.binarytea.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rain.mocking.binarytea.model.Amount;
import rain.mocking.binarytea.model.MenuItem;
import rain.mocking.binarytea.model.Order;
import rain.mocking.binarytea.model.OrderStatus;
import rain.mocking.binarytea.repository.OrderRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
  private final OrderRepository orderRepository;

  public List<Order> getAllOrders() {
    return orderRepository.findAll();
  }

  public Order createOrder(List<MenuItem> itemList, int discount) {
    long total = itemList.stream().mapToLong(MenuItem::getPrice).sum();
    Long pay = total * (discount / 10L);

    Amount amount = Amount.builder().discount(discount).totalAmount(total).payAmount(pay).build();
    Order order =
        Order.builder().amount(amount).status(OrderStatus.ORDERED).items(itemList).build();
    return orderRepository.save(order);
  }
}
