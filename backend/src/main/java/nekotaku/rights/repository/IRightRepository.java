package nekotaku.rights.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import nekotaku.rights.model.Right;

public interface IRightRepository extends JpaRepository<Right, Long> {

}
