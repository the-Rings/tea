package rain.mocking.binarytea.service;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rain.mocking.binarytea.model.Menu;
import rain.mocking.binarytea.repository.MenuRepository;

@Service
@Transactional
@RequiredArgsConstructor
@CacheConfig(cacheNames = "menu")
public class MenuService {
  private final MenuRepository menuRepository;

  public Optional<Menu> save(Menu menu) {
    return Optional.of(menuRepository.save(menu));
  }

  public List<Menu> save(List<Menu> items) {
    return menuRepository.saveAll(items);
  }

  public List<Menu> getAllMenu() {
    return menuRepository.findAll();
  }

  public Optional<Menu> getById(Long id) {
    return menuRepository.findById(id);
  }

  public List<Menu> getByName(String name) {
    return menuRepository.findAll(Example.of(Menu.builder().name(name).build()), Sort.by("id"));
  }

  public List<Menu> getByIdList(List<Long> idList) {
    return menuRepository.findAllById(idList);
  }
}
