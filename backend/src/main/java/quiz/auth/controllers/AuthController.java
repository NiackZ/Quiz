package quiz.auth.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quiz.auth.api.ApiResponse;
import quiz.auth.api.RefreshJwtRequest;
import quiz.auth.api.dto.RegistrationUserDto;
import quiz.auth.model.JwtRequest;
import quiz.auth.service.AuthService;

import javax.servlet.http.HttpServletRequest;

import static quiz.config.JwtRequestFilter.AUTHORIZATION;
import static quiz.config.JwtRequestFilter.BEARER_STR;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;


    @PostMapping("login")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        return authService.login(authRequest);
    }

    @PostMapping("logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String jwt = getHeaderToken(request);
        if (jwt != null) {
            return authService.logout(jwt);
        }
        return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED, "Отсутствует accessToken"), HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("registration")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        return authService.createNewUser(registrationUserDto);
    }

    @PostMapping("token")
    public ResponseEntity<?> getNewAccessToken(@RequestBody RefreshJwtRequest request) {
        System.out.println("Refresh: " + request.getRefreshToken());
        return authService.getAccessToken(request.getRefreshToken());
    }

    @PostMapping("validate-token")
    public ResponseEntity<?> checkAccessToken(HttpServletRequest request) {
        String jwt = getHeaderToken(request);
        if (jwt != null) {
            return authService.checkAccessToken(jwt);
        }
        return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED, "Отсутствует accessToken"), HttpStatus.UNAUTHORIZED);
    }

    private String getHeaderToken(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTHORIZATION);
        System.out.println("getHeaderToken: " + authHeader);
        if (StringUtils.hasText(authHeader) && authHeader.startsWith(BEARER_STR)) {
            String jwt = authHeader.substring(BEARER_STR.length());
            if (!jwt.equalsIgnoreCase("null")) {
                return jwt;
            }
        }
        return null;
    }



}
