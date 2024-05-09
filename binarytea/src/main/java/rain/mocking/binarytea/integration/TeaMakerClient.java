package rain.mocking.binarytea.integration;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author mao
 * @date 2023/9/3
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class TeaMakerClient {
  private final FeignTeaMakerService teaMakerService;

  @SentinelResource(value = "tea-maker", blockHandler = "notFinished")
  public TeaMakerResult makeTea(Long id) {
    ResponseEntity<TeaMakerResult> entity = teaMakerService.makerProcess(id);
    log.info("请求TeaMaker， 响应码：{}", entity.getStatusCode());
    if (HttpStatus.BAD_REQUEST == entity.getStatusCode()) {
      return null;
    }
    return entity.getBody();
  }

  public TeaMakerResult notFinished(Long id, BlockException e) {
    log.warn("Blocked by Sentinel - {}", e.getMessage());
    TeaMakerResult result = new TeaMakerResult();
    result.setFinish(false);
    result.setOrderId(id);
    return result;
  }
}
