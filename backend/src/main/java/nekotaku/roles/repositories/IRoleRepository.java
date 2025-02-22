package nekotaku.roles.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import nekotaku.roles.model.Role;

public interface IRoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
