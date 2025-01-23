package nekotaku.users.api.dto;

import lombok.*;
import nekotaku.users.model.User;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateDTO {

  private Long id;
  @NotNull
  private String username;
  @NotNull
  private String email;
  @NotNull
  private String password;

  private boolean isActive;

  public UserCreateDTO(User author) {
    this.id = author.getId();
    this.username = author.getUsername();
    this.email = author.getEmail();
    this.password = author.getPassword();
    this.isActive = author.isActive();
  }

}
