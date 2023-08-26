package rain.mocking.tea;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("binarytea")
public class BinaryTeaProperties {
  private boolean ready;
  private String openHours;
}
