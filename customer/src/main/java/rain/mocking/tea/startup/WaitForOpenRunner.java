package rain.mocking.tea.startup;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;

import java.util.List;

/**
 * @author mao
 * @date 2023/8/26
 */
@Component
@Order(2)
@Slf4j
public class WaitForOpenRunner implements ApplicationRunner, ApplicationContextAware {
  @Setter private ApplicationContext applicationContext;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    boolean needWait = args.containsOption("wait");
    if (!needWait) {
      log.info("如果没开门，就不用等了。");
    } else {
      List<String> waitSeconds = args.getOptionValues("wait");
      if (!waitSeconds.isEmpty()) {
        int seconds = NumberUtils.parseNumber(waitSeconds.get(0), Integer.class);
        log.info("还没开门，先等{}秒。", seconds);
        Thread.sleep(seconds);
      }
    }
    log.info("这里演示了，如何让一个spring应用程序优雅地停止");
    System.exit(SpringApplication.exit(applicationContext));
  }
}
