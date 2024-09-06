package rain.mocking.teamaker.service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import rain.mocking.teamaker.model.ProcessResult;

@Service
public class OrderService {
  @Value("${maker.id:-1}")
  private long teaMakerId;

  @Value("${maker.time-per-order:500ms}")
  private Duration timePerOrder;

  public ProcessResult make(Long id) {

    try {
      TimeUnit.MILLISECONDS.sleep(timePerOrder.toMillis());
    } catch (InterruptedException e) {
    }
    return ProcessResult.builder().finish(true).orderId(id).teaMakerId(teaMakerId).build();
  }
}
