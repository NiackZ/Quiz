package nekotaku.roles.repositories;

import org.springframework.data.repository.CrudRepository;
import nekotaku.roles.model.Role;

public interface IRoleRepository extends CrudRepository<Role, Integer> {
    Role findByName(String name);
}
