package rain.mocking.binarytea.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;
import rain.mocking.BinaryTeaProperties;

@Component
@RequiredArgsConstructor
public class ShopReadyHealthIndicator extends AbstractHealthIndicator {
  private final BinaryTeaProperties binaryTeaProperties;

  @Override
  protected void doHealthCheck(Health.Builder builder) throws Exception {
    if (binaryTeaProperties == null || !binaryTeaProperties.isReady()) {
      builder.down();
    } else {
      builder.up();
    }
  }
}
