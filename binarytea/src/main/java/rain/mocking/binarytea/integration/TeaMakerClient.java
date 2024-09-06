package rain.mocking.binarytea.integration;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rain.mocking.binarytea.controller.response.SequenceResponse;

/**
 * @author mao
 * @date 2023/9/3
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TeaMakerClient {
  private static final String FEIGN_SEQUENCE_SENTINEL = "FEIGN_SEQUENCE_SENTINEL";
  private static final String MAKE_TEA_SENTINEL = "MAKE_TEA_SENTINEL";
  private final FeignTeaMakerService teaMakerService;

  /**
   * 熔断配置详情（可以在Sentinel控制台操作） { "resource": "MAKE_TEA_SENTINEL", "grade": 2, "count": 3,
   * "timeWindow": 60, "minRequestAmount": 3, "statIntervalMs": 60000 }
   *
   * @param id
   * @return
   */
  @SentinelResource(value = MAKE_TEA_SENTINEL, blockHandler = "notFinished")
  public TeaMakerResult makeTea(Long id) {
    ResponseEntity<TeaMakerResult> entity = teaMakerService.makerProcess(id);
    log.info("请求TeaMaker， 响应码：{}", entity.getStatusCode());
    if (HttpStatus.BAD_REQUEST == entity.getStatusCode()) {
      return null;
    }
    return entity.getBody();
  }

  /**
   * 限流配置（可以在Sentinel控制台操作）{ "resource": "query-order", "grade": 0, "count": 1 }
   *
   * @return
   */
  @SentinelResource(
      value = FEIGN_SEQUENCE_SENTINEL,
      blockHandler = "getSegmentNextBlockHandle",
      fallback = "getSegmentNextFallback")
  public SequenceResponse getSegmentNext() {
    ResponseEntity<Long> entity = teaMakerService.sequenceSegmentNext("maker", "making");
    if (HttpStatus.OK != entity.getStatusCode()) {
      return SequenceResponse.builder().tip("上游服务状态异常：" + entity.getStatusCode()).build();
    }
    return SequenceResponse.builder().tip("成功").id(entity.getBody()).build();
  }

  public SequenceResponse getSegmentNextBlockHandle(BlockException e) {
    // log.warn("Blocked by Sentinel - {}", e.getMessage(), e);
    return SequenceResponse.builder().tip("限流").build();
  }

  public SequenceResponse getSegmentNextFallback(Throwable e) {
    // log.warn("Blocked by Sentinel - {}", e.getMessage(), e);
    return SequenceResponse.builder().tip("熔断").build();
  }

  public TeaMakerResult notFinished(Long id, BlockException e) {
    // log.warn("Blocked by Sentinel - {}", e.getMessage(), e);
    TeaMakerResult result = new TeaMakerResult();
    result.setFinish(false);
    result.setOrderId(id);
    return result;
  }
}
