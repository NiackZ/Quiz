package quiz.users.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import quiz.roles.model.Role;
import quiz.quizzes.model.Quiz;
import quiz.users.api.dto.UserCreateDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false, unique = true)
  private String email;

  @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
  private List<Quiz> quizzes = new ArrayList<>();

  @Column(nullable = false)
  private String password;

  @ColumnDefault("false")
  private boolean deleted;

  @ManyToMany
  @JoinTable(
          name = "users_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private Collection<Role> roles;

  public User(UserCreateDTO userCreateDTO){
    this.id = userCreateDTO.getId();
    this.username = userCreateDTO.getUsername();
    this.email = userCreateDTO.getEmail();
    this.password = userCreateDTO.getPassword();
    this.deleted = userCreateDTO.isDeleted();
  }

}
