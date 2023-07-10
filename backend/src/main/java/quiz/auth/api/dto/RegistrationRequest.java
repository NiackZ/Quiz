package quiz.auth.api.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RegistrationRequest {
    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String password;
    private final boolean deleted = false;

}
