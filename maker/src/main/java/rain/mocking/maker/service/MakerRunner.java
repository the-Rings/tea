package rain.mocking.maker.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author mao
 * @date 2024/8/10 20:53
 */
@Component
@RequiredArgsConstructor
public class MakerRunner implements ApplicationRunner {
  private final SequenceService sequenceService;

  @Override
  public void run(ApplicationArguments args) {
    List<Long> result = sequenceService.sequenceSupply("maker", "making");
    SequenceService.queue.addAll(result);
  }
}
