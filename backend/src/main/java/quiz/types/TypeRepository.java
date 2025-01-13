package quiz.types;

import org.springframework.data.repository.CrudRepository;

public interface TypeRepository extends CrudRepository<Type, Long> {
    boolean existsByName(String name);
}
