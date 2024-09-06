package rain.mocking.binarytea.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_order")
public class Order implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToMany
  @JoinTable(
      name = "t_order_item",
      joinColumns = @JoinColumn(name = "order_id"),
      inverseJoinColumns = @JoinColumn(name = "item_id"))
  @OrderBy
  private List<Menu> items;

  private Long makerId;

  @Embedded private Amount amount;

  @Enumerated private OrderStatus status;
}
