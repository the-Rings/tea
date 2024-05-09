package rain.mocking.customer.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

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
