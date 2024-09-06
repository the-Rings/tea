package rain.mocking.binarytea.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Amount {
  @Column(name = "amount_discount")
  private int discount;

  @Column(name = "amount_total")
  private Long totalAmount;

  @Column(name = "amount_pay")
  private Long payAmount;
}
