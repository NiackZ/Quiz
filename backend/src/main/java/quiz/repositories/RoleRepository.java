package quiz.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import quiz.entities.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findByName(String name);
}
