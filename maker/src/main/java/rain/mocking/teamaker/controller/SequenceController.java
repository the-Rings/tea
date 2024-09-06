package rain.mocking.teamaker.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rain.mocking.teamaker.service.SequenceService;
import rain.mocking.teamaker.util.SnowflakeIdGenerator;

/**
 * @author mao
 * @date 2024/8/10 15:49
 */
@Slf4j
@RestController
@RequestMapping("/maker/sequence")
@RequiredArgsConstructor
public class SequenceController {
  private final SequenceService sequenceService;

  @GetMapping("/segment")
  public ResponseEntity<Long> segmentNext(
      @RequestParam("biz") String bizName, @RequestParam("seg") String segmentName) {
    return ResponseEntity.ok(sequenceService.getNextSequenceId(bizName, segmentName));
  }

  @GetMapping("/snowflake")
  public ResponseEntity<Long> snowflakeNext() {
    SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator(10, 8);
    return ResponseEntity.ok(snowflakeIdGenerator.nextId());
  }
}
