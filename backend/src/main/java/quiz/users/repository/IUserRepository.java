package quiz.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quiz.users.entity.User;

public interface IUserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    User findByUsername(String username);
}
