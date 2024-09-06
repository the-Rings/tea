package rain.mocking.binarytea.controller.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import rain.mocking.binarytea.model.Size;

@Getter
@Setter
public class NewMenuItemForm {
  @NotBlank private String name;
  @NotNull private Long price;
  @NotNull private Size size;
}
