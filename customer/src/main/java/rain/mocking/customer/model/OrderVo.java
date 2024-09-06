package rain.mocking.customer.model;

import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderVo {
  private Long id;
  private Amount amount;
  private List<MenuVo> items;
  private String status;
  private Date createTime;
  private Date updateTime;
}
