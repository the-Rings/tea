package rain.mocking.transaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author mao
 * @date 2023/8/27
 */
@Slf4j
@Component
@Order(2)
@RequiredArgsConstructor
public class TransactionPropagationRunner implements ApplicationRunner {
  private final MixService mixService;
  private final DemoService demoService;

  @Override
  public void run(ApplicationArguments args) {
    log.info("============事务的传播机制，演示学习开始============");
    this.try1();
    demoService.removeAll();

    this.try2();
    demoService.removeAll();

    this.try3();
    demoService.removeAll();

    this.try4();
    demoService.removeAll();
    log.info("============事务的传播机制，演示学习结束============");
  }

  public void try1() {
    log.info("测试事务的传播=====内嵌事务异常，对外部事务无影响======开始");
    try {
      mixService.tryTransactionPropagateNestedError();
    } catch (Exception e) {
    }
    log.info("Names: {}", demoService.showNames());
    log.info("测试事务的传播===========结束");
  }

  public void try2() {
    log.info("测试事务的传播=====外部事务回滚，内嵌事务无论怎么都回滚======开始");
    try {
      mixService.tryTransactionPropagateOuterError();
    } catch (Exception e) {
    }
    log.info("内嵌事务C根本就没有执行，直接回滚");
    log.info("Names: {}", demoService.showNames());
    log.info("测试事务的传播===========结束");
  }

  public void try3() {
    log.info("测试事务的传播======新开事务异常，对外部事务无影响=====开始");
    try {
      mixService.tryTransactionPropagateNewError();
    } catch (Exception e) {
    }
    log.info("Names: {}", demoService.showNames());
    log.info("测试事务的传播===========结束");
  }

  public void try4() {
    log.info("测试事务的传播======外部事务执行异常，对新开事务无影响=====开始");
    try {
      mixService.tryTransactionPropagateNewOuterError();
    } catch (Exception e) {
    }
    log.info("Names: {}", demoService.showNames());
    log.info("测试事务的传播===========结束");
  }
}
