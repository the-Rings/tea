package rain.mocking.dubbo;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public class MenuDubboItem implements Serializable {
  @Serial private static final long serialVersionUID = 1L;

  public Long id;
  public String name;
  public String size;
  public Long price;
  public Date createTime;
  public Date updateTime;
}
