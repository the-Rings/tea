package rain.mocking.binarytea.integration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId = "teaMakerService", name = "tea-maker", path = "/tea-maker")
public interface FeignTeaMakerService {
  @PostMapping(
      value = "/order/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<TeaMakerResult> makerProcess(@PathVariable("id") Long id);

  @GetMapping(value = "/sequence/segment", consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Long> sequenceSegmentNext(@RequestParam("biz") String bizName, @RequestParam("seg") String segmentName);

  @GetMapping(value = "sequence/snowflake", consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Long> sequenceSnowflakeNext();
}
