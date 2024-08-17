package rain.mocking.teamaker.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rain.mocking.teamaker.model.SequenceSegment;
import rain.mocking.teamaker.repository.SequenceSegmentRepository;

/**
 * @author mao
 * @date 2024/8/10 15:47
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SequenceService {
  public static final BlockingQueue<Long> queue = new LinkedBlockingQueue<>();
  private final SequenceSegmentRepository sequenceSegmentRepository;

  public Long getNextSequenceId(String bizName, String segmentName) {
    try {
      Long v = queue.take();
      if (queue.size() <= 1000) {
        List<Long> seqList = this.sequenceSupply(bizName, segmentName);
        if (!seqList.isEmpty()) {
          queue.addAll(seqList);
        }
      }
      return v;
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      log.error("Unknown Error", e);
    }
    return null;
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public List<Long> sequenceSupply(String bizName, String segmentName) {
    List<Long> seqList = new ArrayList<>();
    SequenceSegment sg =
        sequenceSegmentRepository.getBaseMapper().selectOneByBizSegment(bizName, segmentName);
    int rows = sequenceSegmentRepository.getBaseMapper().updateVersionPlus(sg.getId(), bizName, segmentName, sg.getRecordVersion());
    if (rows == 1) {
      Long cv = sg.getCurrentValue();
      Long step = sg.getStep();
      long nextSegment = cv + step;
      for (long i = cv; i < nextSegment; i++) {
        seqList.add(++cv);
      }
      return seqList;
    }
    if (rows > 1) {
      log.error("业务参数设置错误");
      throw new RuntimeException("业务参数设置错误");

    }
    if (rows == 0) {
      log.info("当前存在竞争");
    }
    return new ArrayList<>();
  }
}
