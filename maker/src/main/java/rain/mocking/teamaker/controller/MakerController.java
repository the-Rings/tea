package rain.mocking.teamaker.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rain.mocking.teamaker.model.ProcessResult;
import rain.mocking.teamaker.service.OrderService;

/**
 * @author mao
 * @date 2023/9/3
 */
@Slf4j
@RestController
@RequestMapping("/maker")
@RequiredArgsConstructor
public class MakerController {
  private final OrderService orderService;

  @PostMapping("/order/{id}")
  public ResponseEntity<ProcessResult> makerProcess(@PathVariable Long id) {
    if (id == null || id <= 0) {
      return ResponseEntity.badRequest().build();
    }
    ProcessResult result = orderService.make(id);
    log.info("老师{}号成功完成订单{}的制作", result.getTeaMakerId(), result.getOrderId());
    return ResponseEntity.ok(result);
  }
}
