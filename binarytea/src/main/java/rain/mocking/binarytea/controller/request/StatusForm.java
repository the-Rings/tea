package rain.mocking.binarytea.controller.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusForm {
  @NotNull private Long id;
  @NotBlank private String status;
}
