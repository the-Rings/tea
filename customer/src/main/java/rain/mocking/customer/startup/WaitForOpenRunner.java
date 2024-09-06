package rain.mocking.customer.startup;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;
import org.springframework.web.client.RestTemplate;

/**
 * @author mao
 * @date 2023/8/26
 */
@Component
@Order(1)
@Slf4j
@RequiredArgsConstructor
public class WaitForOpenRunner implements ApplicationRunner, ApplicationContextAware {
  private final RestTemplate restTemplate;
  @Setter private ApplicationContext applicationContext;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    log.info("这里演示了@order用法，如果有多个类实现了ApplicationRunner，那么它们就是同一种类型，Order在这个小范围内按照顺序执行");
    log.info("执行ApplicationRunner order=2");
    boolean flag = isOpen(); // 开门了么？
    flag = flag || (waitForOpen(args) && isOpen()); // 没开门，想等一下再看看
    if (!flag) { // 没开门就退出
      log.info("店没开门，走了");
      log.info("这里演示了，如何让一个spring应用程序优雅地停止");
      System.exit(SpringApplication.exit(applicationContext));
    } else {
      log.info("店开门了，进去看看");
    }
  }

  private boolean waitForOpen(ApplicationArguments args) throws InterruptedException {
    boolean needWait = args.containsOption("wait");
    if (!needWait) {
      log.info("如果没开门，就不用等了。");
    } else {
      List<String> waitSeconds = args.getOptionValues("wait");
      if (!waitSeconds.isEmpty()) {
        int seconds = NumberUtils.parseNumber(waitSeconds.get(0), Integer.class);
        log.info("还没开门，先等{}秒。", seconds);
        Thread.sleep(seconds * 1000L);
      }
    }
    return needWait;
  }

  private boolean isOpen() {
    String binarytea = "http://127.0.0.1:8080";
    ResponseEntity<String> entity = null;
    try {
      entity = restTemplate.getForEntity(binarytea + "/menu", String.class);
      return entity.getStatusCode().is2xxSuccessful();
    } catch (Exception e) {
      log.warn("应该还没开门，访问出错：{}", e.getMessage());
    }
    return false;
  }
}
