package quiz.rights.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quiz.rights.model.Right;

public interface IRightRepository extends JpaRepository<Right, Long> {

}
