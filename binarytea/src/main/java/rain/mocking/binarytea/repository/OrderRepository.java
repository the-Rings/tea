package rain.mocking.binarytea.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import rain.mocking.binarytea.model.Order;
import rain.mocking.binarytea.model.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Long> {

  List<Order> findByStatusEqualsAndIdInOrderById(OrderStatus status, List<Long> idList);

  List<Order> findByStatusEquals(OrderStatus status);
}
