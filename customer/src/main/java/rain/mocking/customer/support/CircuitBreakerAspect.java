package rain.mocking.customer.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author mao
 * @date 2023/9/2
 */
@Aspect
@Slf4j
@Order(1)
// @Component
public class CircuitBreakerAspect {
  private static final Integer THRESHOLD = 3;
  private final Map<String, AtomicInteger> errorCounter = new ConcurrentHashMap<>();
  private final Map<String, AtomicInteger> probeCounter = new ConcurrentHashMap<>();

  @Around("execution(* rain.mocking.customer.integratoin..*(..))")
  public Object doWithCircuitBreaker(ProceedingJoinPoint pjp) throws Throwable {
    String signature = pjp.getSignature().toLongString();
    log.info("带断路器保护，执行{}方法", signature);
    Object retVal;

    try {
      if (!errorCounter.containsKey(signature)) {
        resetCounter(signature);
      }
      if (errorCounter.get(signature).get() >= THRESHOLD
          && probeCounter.get(signature).get() < THRESHOLD) {
        log.warn("断路器打开， 第{}次直接返回503", probeCounter.get(signature).incrementAndGet());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE);
      }
      retVal = pjp.proceed();
      resetCounter(signature);
    } catch (Throwable t) {
      log.warn("错误计数{}次，抛出{}异常", errorCounter.get(signature).incrementAndGet(), t.getMessage());
      probeCounter.get(signature).set(0);
      throw t;
    }
    return retVal;
  }

  private void resetCounter(String signature) {
    errorCounter.put(signature, new AtomicInteger(0));
    probeCounter.put(signature, new AtomicInteger(0));
  }
}
