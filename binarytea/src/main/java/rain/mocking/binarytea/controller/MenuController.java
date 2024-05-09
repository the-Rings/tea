package rain.mocking.binarytea.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import rain.mocking.binarytea.controller.request.NewMenuItemForm;
import rain.mocking.binarytea.model.Menu;
import rain.mocking.binarytea.model.Size;
import rain.mocking.binarytea.service.MenuService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@ResponseBody
@RequestMapping("/menu")
@Slf4j
@RequiredArgsConstructor
public class MenuController {
  private final MenuService menuService;

  @GetMapping(params = "!name")
  public List<Menu> listMenus() {
    return menuService.getAllMenu();
  }

  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Optional<Menu> detailMenu(@PathVariable Long id) {
    return menuService.getById(id);
  }

  @RequestMapping(params = "name", method = RequestMethod.GET)
  public List<Menu> listMenusByName(@RequestParam String name) {
    return menuService.getByName(name);
  }

  @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public Optional<Menu> createByForm(
      @Valid NewMenuItemForm form, BindingResult result, HttpServletResponse response) {
    if (result.hasErrors()) {
      log.warn("绑定参数错误：{}", result.getAllErrors());
      response.setStatus(HttpStatus.BAD_REQUEST.value());
      return Optional.empty();
    }
    Menu item =
        Menu.builder().name(form.getName()).price(form.getPrice()).size(form.getSize()).build();
    return menuService.save(item);
  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<List<Menu>> createBatch(@RequestParam("file") MultipartFile file) {
    List<Menu> menuList = new ArrayList<>();
    URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri();
    log.info("Current URI: {}", uri);
    if (file == null || file.isEmpty()) {
      log.warn("File can NOT be null or empty.");
      return ResponseEntity.badRequest().body(menuList);
    }
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
      menuList =
          reader
              .lines()
              .map(StringUtils::split)
              .filter(f -> f != null && f.length == 3)
              .map(
                  f ->
                      Menu.builder()
                          .name(f[0])
                          .size(Size.valueOf(f[1]))
                          .price(Long.valueOf(f[2]))
                          .build())
              .collect(Collectors.toList());
      return ResponseEntity.created(uri).body(menuService.save(menuList));
    } catch (Exception e) {
      log.error("Exception occurred while creating menu list.", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(menuList);
    }
  }
}
