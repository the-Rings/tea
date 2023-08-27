package rain.mocking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.core.annotation.Order;

@Slf4j
@ConfigurationPropertiesScan
@SpringBootApplication
public class BinaryTeaApplication {

  public static void main(String... args) {
    SpringApplication.run(BinaryTeaApplication.class, args);
  }
}
