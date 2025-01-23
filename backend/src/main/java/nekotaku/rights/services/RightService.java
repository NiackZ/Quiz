package nekotaku.rights.services;

import liquibase.repackaged.org.apache.commons.collections4.IteratorUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import nekotaku.rights.model.Right;
import nekotaku.rights.repository.IRightRepository;
import nekotaku.users.repository.IUserRepository;
import nekotaku.utils.Utils;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@AllArgsConstructor
public class RightService {
    private final IRightRepository rightRepository;
    private final IUserRepository userRepository;

    public List<Right> findAll() {
        return IteratorUtils.toList(this.rightRepository.findAll().iterator());
    }

    public boolean checkRights(@NotNull Long userId, @NotNull List<String> rights) {
        List<String> userRights = this.userRepository.findUserRightsById(userId);
        return Utils.listContainsValuesIgnoreCase(userRights, rights);
    }
}
