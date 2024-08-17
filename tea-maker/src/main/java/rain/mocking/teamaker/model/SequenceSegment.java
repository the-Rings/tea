package rain.mocking.teamaker.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * @author mao
 * @date 2024/8/10 15:38
 */
@Getter
@Setter
@TableName(value = "t_sequence_segment", schema = "maker", autoResultMap = true)
public class SequenceSegment {
  @TableId(type = IdType.AUTO)
  private Long id;

  @TableField("biz_name")
  private String bizName;

  @TableField("segment_name")
  private String segmentName;

  @TableField("current_value")
  private Long currentValue;

  @TableField("max_value")
  private Long maxValue;

  @TableField("step")
  private Long step;

  @TableField("record_version")
  private Long recordVersion;

  @TableField("retry_times")
  private Long retryTimes;
}
