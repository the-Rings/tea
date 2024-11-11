package rain.mocking.binarytea.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;
import rain.mocking.binarytea.config.BinaryTeaProperties;

@Component
@Endpoint(id = "shop")
@RequiredArgsConstructor
public class ShopEndpoint {
  private final BinaryTeaProperties binaryTeaProperties;

  @ReadOperation
  public String state() {
    if (binaryTeaProperties == null || !binaryTeaProperties.isReady()) {
      return "We're not ready.";
    } else {
      return "We open " + binaryTeaProperties.getOpenHours() + ".";
    }
  }
}
