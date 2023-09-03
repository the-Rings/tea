package rain.mocking.customer.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class StatusForm {
  private Long id;
  private String status;
}
