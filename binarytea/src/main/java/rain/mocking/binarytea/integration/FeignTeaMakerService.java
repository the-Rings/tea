package rain.mocking.binarytea.integration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(contextId = "teaMakerService", name = "tea-maker", path = "/tea-maker")
public interface FeignTeaMakerService {
  @PostMapping(
      value = "/order/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<TeaMakerResult> makerProcess(@PathVariable Long id);
}
