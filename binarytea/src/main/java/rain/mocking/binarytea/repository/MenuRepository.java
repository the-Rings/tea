package rain.mocking.binarytea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rain.mocking.binarytea.model.MenuItem;
import rain.mocking.binarytea.model.Size;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<MenuItem, Long> {
  Optional<MenuItem> findByNameAndSize(String name, Size size);
}
