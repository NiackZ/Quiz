package nekotaku.rights.services;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import nekotaku.rights.model.Right;
import nekotaku.rights.repository.IRightRepository;
import nekotaku.users.repository.IUserRepository;
import nekotaku.utils.Utils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RightService {
    private final IRightRepository rightRepository;
    private final IUserRepository userRepository;

    public List<Right> findAll() {
        return rightRepository.findAll();
    }

    public boolean checkRights(@NotNull Long userId, @NotNull List<String> rights) {
        return Utils.listContainsValuesIgnoreCase(this.userRepository.findUserRightsById(userId), rights);
    }
}
