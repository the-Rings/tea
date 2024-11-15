package rain.mocking;

import java.sql.Connection;
import javax.sql.DataSource;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;

@Slf4j
@Order(1)
@ConfigurationPropertiesScan
@SpringBootApplication
public class TransactionPropagationApplication
    implements ApplicationRunner, ApplicationContextAware {
  @Setter private ApplicationContext applicationContext;

  public static void main(String... args) {
    SpringApplication.run(TransactionPropagationApplication.class, args);
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {

    DataSource dataSource = applicationContext.getBean("dataSource", DataSource.class);
    Connection connection = dataSource.getConnection();
    log.info("当前的H2连接URL: {}", connection.getMetaData().getURL());
  }
}
