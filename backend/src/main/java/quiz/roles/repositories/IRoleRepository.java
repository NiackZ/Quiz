package quiz.roles.repositories;

import org.springframework.data.repository.CrudRepository;
import quiz.roles.model.Role;

public interface IRoleRepository extends CrudRepository<Role, Integer> {
    Role findByName(String name);
}
