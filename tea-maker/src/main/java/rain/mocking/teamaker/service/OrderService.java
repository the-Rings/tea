package rain.mocking.teamaker.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import rain.mocking.teamaker.model.ProcessResult;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
public class OrderService {
  @Value("${tea-maker.id:-1}")
  private long teaMakerId;

  @Value("${tea-maker.time-per-order:500ms}")
  private Duration timePerOrder;

  public ProcessResult make(Long id) {

    try {
      TimeUnit.MILLISECONDS.sleep(timePerOrder.toMillis());
    } catch (InterruptedException e) {
    }
    return ProcessResult.builder().finish(true).orderId(id).teaMakerId(teaMakerId).build();
  }
}
