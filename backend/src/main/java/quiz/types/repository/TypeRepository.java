package quiz.types.repository;

import org.springframework.data.repository.CrudRepository;
import quiz.types.model.Type;

public interface TypeRepository extends CrudRepository<Type, Long> {
}
