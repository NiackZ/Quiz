package quiz.auth.service;

import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import quiz.auth.api.ApiResponse;
import quiz.auth.model.JwtRequest;
import quiz.auth.model.JwtResponse;
import quiz.auth.api.dto.RegistrationUserDto;
import quiz.auth.api.dto.UserDto;
import quiz.users.model.User;
import quiz.users.service.UserService;
import quiz.utils.JwtUtil;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final Map<String, String> refreshStorage = new HashMap<>();//redis

    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, "Пароли не совпадают"), HttpStatus.BAD_REQUEST);
        }
        if (userService.findByUserName(registrationUserDto.getUsername()) != null) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, "Пользователь с указанным именем уже существует"), HttpStatus.BAD_REQUEST);
        }
        User user = userService.createNewUser(registrationUserDto);
        return ResponseEntity.ok(new UserDto(user.getId(), user.getUsername(), user.getEmail()));
    }

    public ResponseEntity<?> login(@NonNull @RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED, "Неправильный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String accessToken = jwtUtil.generateAccessToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);
        refreshStorage.put(authRequest.getUsername(), refreshToken);
        return ResponseEntity.ok(new JwtResponse(accessToken, refreshToken));
    }

    public ResponseEntity<?> logout(@NonNull @RequestBody String jwt) {
        String username = jwtUtil.extractUsername(jwt);
        refreshStorage.remove(username);
        return ResponseEntity.ok("OK");
    }

    public ResponseEntity<?> getAccessToken(@NonNull String refreshToken) {
        if (jwtUtil.validateRefreshToken(refreshToken)) {
            Claims claims = jwtUtil.getRefreshClaims(refreshToken);
            String username = claims.getSubject();
            String saveRefreshToken = refreshStorage.get(username);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                UserDetails userDetails = userService.loadUserByUsername(username);
                String accessToken = jwtUtil.generateAccessToken(userDetails);
                return ResponseEntity.ok(new JwtResponse(accessToken, null));
            }
        }
        return ResponseEntity.ok(new JwtResponse(null, null));
    }

    public ResponseEntity<?> refresh(@NonNull String refreshToken) {
        if (jwtUtil.validateRefreshToken(refreshToken)) {
            Claims claims = jwtUtil.getRefreshClaims(refreshToken);
            String username = claims.getSubject();
            String saveRefreshToken = refreshStorage.get(username);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                UserDetails userDetails = userService.loadUserByUsername(username);
                String accessToken = jwtUtil.generateAccessToken(userDetails);
                String newRefreshToken = jwtUtil.generateRefreshToken(userDetails);
                refreshStorage.put(username, newRefreshToken);
                return ResponseEntity.ok(new JwtResponse(accessToken, newRefreshToken));
            }
        }
        return new ResponseEntity<>("Невалидный JWT токен", HttpStatus.BAD_REQUEST);
    }

}
