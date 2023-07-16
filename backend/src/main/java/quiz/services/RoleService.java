package quiz.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import quiz.entities.Role;
import quiz.repositories.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER");
    }
}
