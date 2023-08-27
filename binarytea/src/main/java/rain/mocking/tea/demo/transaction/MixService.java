package rain.mocking.tea.demo.transaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author mao
 * @date 2023/8/27
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MixService {
  private final DemoService demoService;

  @Transactional(propagation = Propagation.REQUIRED)
  public void tryTransactionPropagateNestedError() {
    log.info("开外部启事务A");
    demoService.insertRecordRequired();
    try {
      demoService.insertRecordNestedWithError();
    } catch (Exception e) {
    }
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void tryTransactionPropagateOuterError() {
    log.info("开外部启事务A");
    try {
      demoService.insertRecordRequiredError();
    } catch (Exception e) {
    }
    demoService.insertRecordNested();
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void tryTransactionPropagateNewError() {
    log.info("开外部启事务A");
    demoService.insertRecordRequired();
    try {
      demoService.insertRecordRequiresNew();
    } catch (Exception e) {
    }
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void tryTransactionPropagateNewOuterError() {
    log.info("开外部启事务A");
    try {
      demoService.insertRecordRequiredError();
    } catch (Exception e) {
    }
    demoService.insertRecordRequiresNew();
  }
}
