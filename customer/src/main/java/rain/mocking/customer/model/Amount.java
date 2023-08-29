package rain.mocking.customer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Amount {
  private int discount;
  private Long totalAmount;
  private Long payAmount;
}
