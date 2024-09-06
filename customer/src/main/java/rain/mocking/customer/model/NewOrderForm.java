package rain.mocking.customer.model;

import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewOrderForm {
  private List<String> itemIdList;
  private int discount;
}
