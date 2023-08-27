package rain.mocking.binarytea.service;

import lombok.RequiredArgsConstructor;
import org.joda.money.Money;
import org.springframework.stereotype.Service;
import rain.mocking.binarytea.model.Amount;
import rain.mocking.binarytea.model.MenuItem;
import rain.mocking.binarytea.model.Order;
import rain.mocking.binarytea.model.OrderStatus;
import rain.mocking.binarytea.repository.OrderRepository;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
  private final OrderRepository orderRepository;

  public List<Order> getAllOrders() {
    return orderRepository.findAll();
  }

  @RolesAllowed({"MANAGER", "TEA_MAKER"})
  public Order createOrder(List<MenuItem> itemList, int discount) {
    Money total =
        itemList.stream()
            .map(i -> i.getPrice())
            .collect(Collectors.collectingAndThen(Collectors.toList(), l -> Money.total(l)));
    Money pay = total.multipliedBy(discount / 100d, RoundingMode.HALF_DOWN);

    Amount amount = Amount.builder().discount(discount).totalAmount(total).payAmount(pay).build();
    Order order =
        Order.builder().amount(amount).status(OrderStatus.ORDERED).items(itemList).build();
    return orderRepository.save(order);
  }
}
