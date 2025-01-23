package nekotaku.roles.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import nekotaku.roles.model.Role;
import nekotaku.roles.repositories.IRoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final IRoleRepository IRoleRepository;

    public Role getUserRole() {
        return IRoleRepository.findByName("USER");
    }
}
