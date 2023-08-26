package rain.mocking.tea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class TeaApplication {

  public static void main(String... args) {
    SpringApplication.run(TeaApplication.class, args);
  }
}
