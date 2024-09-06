package rain.mocking.binarytea.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import rain.mocking.binarytea.model.Menu;
import rain.mocking.binarytea.model.Size;

public interface MenuRepository extends JpaRepository<Menu, Long> {
  Optional<Menu> findByNameAndSize(String name, Size size);
}
