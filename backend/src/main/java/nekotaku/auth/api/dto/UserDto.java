package nekotaku.auth.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nekotaku.users.model.User;

@Data
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String email;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
