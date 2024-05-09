package rain.mocking.customer.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class MenuVo {
  private Long id;
  private String name;
  private String size;
  private Long price;
}
