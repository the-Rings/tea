package rain.mocking.binarytea.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@Data
@ConfigurationProperties(prefix = "binarytea")
@RefreshScope // 支持更新配置后自动生效
public class BinaryTeaProperties {
  private boolean ready;
  private String openHours;
  private int discount = 100;

  public boolean isReady() {
    return ready;
  }
}
