package nekotaku.roles.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import nekotaku.roles.model.Role;
import nekotaku.roles.repositories.IRoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final IRoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName("USER");
    }
}
