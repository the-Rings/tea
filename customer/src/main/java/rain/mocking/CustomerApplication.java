package rain.mocking;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * @author mao
 * @date 2023/8/26
 */
@SpringBootApplication
public class CustomerApplication {
  public static void main(String[] args) {
    SpringApplication.run(CustomerApplication.class, args);
  }

  /**
   * 如果命令行给了wait选项，返回0，否则返回1
   *
   * @param args 命令行参数
   * @return ExitCodeGenerator
   */
  @Bean
  public ExitCodeGenerator waitExitConeGenerator(ApplicationArguments args) {
    return () -> (args.containsOption("wait") ? 0 : 1);
  }

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder
        .setConnectTimeout(Duration.ofSeconds(1)) // 连接超时
        .setReadTimeout(Duration.ofSeconds(5)) // 读取超时
        .build();
  }
}
