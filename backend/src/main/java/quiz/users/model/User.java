package quiz.users.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import quiz.quizzes.model.Quiz;
import quiz.rights.model.Right;
import quiz.roles.model.Role;
import quiz.users.api.dto.UserCreateDTO;

import javax.persistence.*;
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

  @Column(name = "avatar_url", length = 500)
  private String avatarURL;

  @OneToMany
  @JoinTable(
          name = "user_quiz",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "quiz_id")
  )
  private List<Quiz> quizzes;

  @Column(nullable = false)
  private String password;

  @ColumnDefault("true")
  private boolean isActive;

  @ManyToMany
  @JoinTable(
          name = "users_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private List<Role> roles;

  @ManyToMany
  @JoinTable(
          name = "users_rights",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "right_id")
  )
  private List<Right> rights;

  public User(UserCreateDTO userCreateDTO){
    this.id = userCreateDTO.getId();
    this.username = userCreateDTO.getUsername();
    this.email = userCreateDTO.getEmail();
    this.password = userCreateDTO.getPassword();
    this.isActive = userCreateDTO.isActive();
  }

}
