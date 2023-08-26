package rain.mocking.tea.startup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author mao
 * @date 2023/8/26
 */
@Component
@Slf4j
@Order(1)
public class ArgsConsolePrinterRunner implements CommandLineRunner {

  @Override
  public void run(String... args) throws Exception {
    log.info("Spring容器启动后立即执行以下逻辑，order=1，打印命令行参数功能");
    log.info("传入了{}个参数，分别是: {}", args.length, StringUtils.arrayToCommaDelimitedString(args));
  }
}
