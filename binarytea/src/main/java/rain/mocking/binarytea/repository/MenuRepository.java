package rain.mocking.binarytea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rain.mocking.binarytea.model.Menu;
import rain.mocking.binarytea.model.Size;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
  Optional<Menu> findByNameAndSize(String name, Size size);
}
