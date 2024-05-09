package rain.mocking.teamaker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rain.mocking.teamaker.model.TeaMaker;

public interface TeaMakerRepository extends JpaRepository<TeaMaker, Long> {}
