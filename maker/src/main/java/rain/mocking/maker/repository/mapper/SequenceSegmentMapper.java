package rain.mocking.maker.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import rain.mocking.maker.model.SequenceSegment;

@Mapper
public interface SequenceSegmentMapper extends BaseMapper<SequenceSegment> {
  SequenceSegment selectOneByBizSegment(
      @Param("biz_name") String bizName, @Param("segment_name") String segmentName);

  int updateVersionPlus(
      @Param("id") Long id,
      @Param("biz_name") String bizName,
      @Param("segment_name") String segmentName,
      @Param("record_version") Long record_version);
}
