package quiz.auth.controllers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

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
    public ResponseEntity<?> getToken(@RequestBody RefreshJwtRequest request) {
        log.info("Refresh: {}", request.getRefreshToken()
                .substring(request.getRefreshToken().length() - 10));
        return authService.getToken(request.getRefreshToken());
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
        log.info("getHeaderToken: {}", authHeader);
        if (StringUtils.hasText(authHeader) && authHeader.startsWith(BEARER_STR)) {
            String jwt = authHeader.substring(BEARER_STR.length());
            if (!jwt.equalsIgnoreCase("null")) {
                return jwt;
            }
        }
        return null;
    }

}
