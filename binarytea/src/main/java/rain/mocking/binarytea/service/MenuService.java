package rain.mocking.binarytea.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rain.mocking.binarytea.model.MenuItem;
import rain.mocking.binarytea.model.Size;
import rain.mocking.binarytea.repository.MenuRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@CacheConfig(cacheNames = "menu")
public class MenuService {
  private final MenuRepository menuRepository;

  public Optional<MenuItem> save(MenuItem menuItem) {
    return Optional.of(menuRepository.save(menuItem));
  }

  public List<MenuItem> save(List<MenuItem> items) {
    return menuRepository.saveAll(items);
  }

  @Cacheable
  public List<MenuItem> getAllMenu() {
    return menuRepository.findAll();
  }

  public Optional<MenuItem> getById(Long id) {
    return menuRepository.findById(id);
  }

  public List<MenuItem> getByName(String name) {
    return menuRepository.findAll(Example.of(MenuItem.builder().name(name).build()), Sort.by("id"));
  }

  public List<MenuItem> getByIdList(List<Long> idList) {
    return menuRepository.findAllById(idList);
  }

  @Cacheable(key = "#root.methodName + '-' + #name + '-' + #size")
  public Optional<MenuItem> getByNameAndSize(String name, Size size) {
    return menuRepository.findByNameAndSize(name, size);
  }
}
