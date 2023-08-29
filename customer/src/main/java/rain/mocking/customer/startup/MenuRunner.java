package rain.mocking.customer.startup;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import rain.mocking.customer.model.MenuItem;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
@Order(3)
@RequiredArgsConstructor
public class MenuRunner implements ApplicationRunner {
  private final RestTemplate restTemplate;

  @Value("${binarytea.url}")
  private String binarytea;

  @Override
  public void run(ApplicationArguments args) {
    MenuItem item = restTemplate.getForObject(binarytea + "/menu/{id}", MenuItem.class, 1);
    log.info("菜单上第一个是{}", item);

    String coffee =
        restTemplate.getForObject(
            binarytea + "/menu?name={name}",
            String.class,
            Collections.singletonMap("name", "Java咖啡"));
    log.info("有Java咖啡么？{}", coffee);

    String menuJson = restTemplate.getForObject(binarytea + "/menu", String.class);
    log.info("完整菜单：{}", menuJson);

    getAllMenu();
  }

  private void getAllMenu() {
    ParameterizedTypeReference<List<MenuItem>> typeReference =
            new ParameterizedTypeReference<>() {
            };
    URI uri = UriComponentsBuilder.fromUriString(binarytea + "/menu").build().toUri();
    RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
    ResponseEntity<List<MenuItem>> response = restTemplate.exchange(request, typeReference);
    log.info("响应码：{}", response.getStatusCode());
    Objects.requireNonNull(response.getBody()).forEach(menuItem -> log.info("条目：{}", menuItem));
  }
}
