package quiz.auth.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quiz.auth.model.JwtRequest;
import quiz.auth.service.AuthService;
import quiz.dtos.RegistrationUserDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        return authService.login(authRequest);
    }

    @PostMapping("registration")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        return authService.createNewUser(registrationUserDto);
    }

    @PostMapping("token")
    public ResponseEntity<?> getNewAccessToken(@RequestBody String refreshToken) {
        return authService.getAccessToken(refreshToken);
    }

    @PostMapping("refresh")
    public ResponseEntity<?> getNewRefreshToken(@RequestBody String refreshToken) {
        return authService.refresh(refreshToken);
    }

}
