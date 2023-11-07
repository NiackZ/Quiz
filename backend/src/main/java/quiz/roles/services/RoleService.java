package quiz.roles.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import quiz.roles.model.Role;
import quiz.roles.repositories.IRoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final IRoleRepository IRoleRepository;

    public Role getUserRole() {
        return IRoleRepository.findByName("USER");
    }
}
