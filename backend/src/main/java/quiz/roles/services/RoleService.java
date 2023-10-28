package quiz.roles.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import quiz.roles.model.Role;
import quiz.roles.repositories.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName("USER");
    }
}
