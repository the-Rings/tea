package rain.mocking.binarytea.controller.request;

import lombok.Getter;
import lombok.Setter;
import org.joda.money.Money;
import rain.mocking.binarytea.model.Size;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class NewMenuItemForm {
    @NotBlank
    private String name;
    @NotNull
    private Money price;
    @NotNull
    private Size size;
}
