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
import quiz.auth.api.dto.RegistrationUserDto;
import quiz.auth.api.dto.UserDto;
import quiz.auth.model.JwtRequest;
import quiz.auth.model.JwtResponse;
import quiz.users.model.User;
import quiz.users.service.UserService;
import quiz.utils.JwtUtil;
import quiz.utils.model.TokenStatus;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
        Map<String, Object> map = new HashMap<>();
        map.put("user", userService.getByUserName(userDetails.getUsername()));
        map.put("jwt", new JwtResponse(accessToken, refreshToken));
        return ResponseEntity.ok(map);
    }

    public ResponseEntity<?> logout(@NonNull @RequestBody String jwt) {
        String username = jwtUtil.extractUsername(jwt);
        refreshStorage.remove(username);
        return ResponseEntity.ok("OK");
    }

    public ResponseEntity<?> checkAccessToken(@NonNull @RequestBody String jwt) {
        if (jwtUtil.validateAccessToken(jwt) == TokenStatus.VALID) {
            Map<String, Object> map = new HashMap<>();
            map.put("user", userService.getByUserName(jwtUtil.extractUsername(jwt)));
            return ResponseEntity.ok(map);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<?> getAccessToken(@NonNull String refreshToken) {
        try {
            JwtResponse response = processRefreshToken(refreshToken);
            return ResponseEntity.ok(new JwtResponse(response.getAccessToken(), null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private ResponseEntity<?> refresh(@NonNull String refreshToken) {
        try {
            JwtResponse response = processRefreshToken(refreshToken);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private JwtResponse processRefreshToken(String refreshToken) {
        if (jwtUtil.validateRefreshToken(refreshToken) != TokenStatus.VALID) {
            throw new IllegalArgumentException("Невалидный refresh токен");
        }

        Claims claims = jwtUtil.getRefreshClaims(refreshToken);
        String username = claims.getSubject();
        String savedRefreshToken = refreshStorage.get(username);

        if (savedRefreshToken == null || !savedRefreshToken.equals(refreshToken)) {
            throw new IllegalArgumentException("Токен отсутствует или не совпадает");
        }

        UserDetails userDetails = userService.loadUserByUsername(username);
        String accessToken = jwtUtil.generateAccessToken(userDetails);

        // Получаем дату истечения refreshToken в виде LocalDateTime
        LocalDateTime expirationDate = claims.getExpiration().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        // Рассчитываем, сколько времени осталось до истечения
        LocalDateTime now = LocalDateTime.now();
        long daysUntilExpiration = now.until(expirationDate, java.time.temporal.ChronoUnit.DAYS);

        // Обновляем refreshToken, если осталось меньше 7 дней
        String newRefreshToken = refreshToken;
        if (daysUntilExpiration < 7) {
            newRefreshToken = jwtUtil.generateRefreshToken(userDetails);
            refreshStorage.put(username, newRefreshToken);
        }

        return new JwtResponse(accessToken, newRefreshToken);
    }

}
