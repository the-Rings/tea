package rain.mocking.customer.support;

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

/**
 * @author mao
 * @date 2023/8/31
 */
@Aspect
@Slf4j
@Order(0)
// @Component
public class RetryAspect {
  private static final int THRESHOLD = 3;
  private static final int DURATION = 100;

  @Around("execution(* rain.mocking.customer.integratoin..*(..))")
  public Object doWithRetry(ProceedingJoinPoint jp) throws Throwable {
    String signature = jp.getSignature().toLongString();
    log.info("带重试机制调用{}方法", signature);
    Object ret;
    Exception lastEx = null;
    for (int i = 0; i < THRESHOLD; i++) {
      try {
        ret = jp.proceed();
        log.info("在第{}次完成{}调用", i, signature);
        return ret;
      } catch (Exception e) {
        log.warn("执行失败", e);
        lastEx = e;
        TimeUnit.MICROSECONDS.sleep(DURATION);
      }
    }
    log.error("方法{}族中执行失败， 抛出异常{}", signature, lastEx);
    throw lastEx;
  }
}
