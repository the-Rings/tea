package rain.mocking;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "binarytea")
public class BinaryTeaProperties {
  private boolean ready;
  private String openHours;
}
