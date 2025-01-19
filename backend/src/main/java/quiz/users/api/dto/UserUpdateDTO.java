package quiz.users.api.dto;

import lombok.*;
import quiz.utils.model.Image;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {
    private long id;
    private String password;
    private String newPassword;
    private String email;
    private Image avatar;
}