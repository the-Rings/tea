package rain.mocking.binarytea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rain.mocking.binarytea.model.TeaMaker;

public interface TeaMakerRepository extends JpaRepository<TeaMaker, Long> {}
