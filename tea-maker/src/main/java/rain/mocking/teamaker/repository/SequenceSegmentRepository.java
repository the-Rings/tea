package rain.mocking.teamaker.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import rain.mocking.teamaker.model.SequenceSegment;
import rain.mocking.teamaker.repository.mapper.SequenceSegmentMapper;

/**
 * @author mao
 * @date 2024/8/10 15:42
 */
@Service
public class SequenceSegmentRepository extends ServiceImpl<SequenceSegmentMapper, SequenceSegment>
    implements IService<SequenceSegment> {}
