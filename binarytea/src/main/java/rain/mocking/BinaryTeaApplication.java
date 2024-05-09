package rain.mocking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Slf4j
@EnableFeignClients
@EnableScheduling
@ConfigurationPropertiesScan
@SpringBootApplication
public class BinaryTeaApplication {

  public static void main(String... args) {
    SpringApplication.run(BinaryTeaApplication.class, args);
  }

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder
        .setConnectTimeout(Duration.ofSeconds(1)) // 连接超时
        .setReadTimeout(Duration.ofSeconds(5)) // 读取超时
        .build();
  }
}
