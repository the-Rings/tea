package rain.mocking.binarytea.controller.request;

import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewOrderForm {
  @NotEmpty private List<String> itemIdList;

  @Min(50)
  @Max(100)
  private int discount;
}
