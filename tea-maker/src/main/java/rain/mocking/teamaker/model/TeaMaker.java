package rain.mocking.teamaker.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_tea_maker", schema = "maker", autoResultMap = true)
public class TeaMaker {
  @TableId(type = IdType.AUTO)
  private Long id;

  private String name;
}
