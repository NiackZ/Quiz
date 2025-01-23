package nekotaku.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import nekotaku.users.model.User;

import java.util.List;

public interface IUserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    User findByUsername(String username);
    @Query("SELECT r.name FROM User u JOIN u.rights r WHERE u.id = :userId")
    List<String> findUserRightsById(Long userId);

    @Transactional
    @Modifying
    @Query("update User u set u.avatarURL = ?1 where u.id = ?2")
    void updateAvatar(String avatarURL, Long id);
}
