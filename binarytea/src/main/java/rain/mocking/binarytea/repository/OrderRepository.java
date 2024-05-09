package rain.mocking.binarytea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rain.mocking.binarytea.model.Order;
import rain.mocking.binarytea.model.OrderStatus;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

  List<Order> findByStatusEqualsAndIdInOrderById(OrderStatus status, List<Long> idList);

  List<Order> findByStatusEquals(OrderStatus status);
}
