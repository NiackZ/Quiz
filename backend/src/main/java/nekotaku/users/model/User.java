package nekotaku.users.model;

import jakarta.persistence.*;
import lombok.*;
import nekotaku.rights.model.Right;
import nekotaku.roles.model.Role;
import nekotaku.users.api.dto.UserCreateDTO;
import org.hibernate.annotations.ColumnDefault;

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
