package rain.mocking.binarytea.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rain.mocking.binarytea.controller.response.SequenceResponse;
import rain.mocking.binarytea.integration.TeaMakerClient;

/**
 * @author mao
 * @date 2024/8/10 20:19
 */
@RestController
@RequestMapping("/sequence")
@RequiredArgsConstructor
public class SequenceController {
  private final TeaMakerClient teaMakerClient;

  @GetMapping
  public ResponseEntity<SequenceResponse> sequenceNext() {
    return ResponseEntity.ok(teaMakerClient.getSegmentNext());
  }
}
