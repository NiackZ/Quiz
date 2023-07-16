package quiz.controllers;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import quiz.dtos.JwtRequest;
import quiz.dtos.RegistrationUserDto;
import quiz.services.AuthService;
import quiz.utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {



    private final AuthService authService;

    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        return authService.createAuthToken(authRequest);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        return authService.createNewUser(registrationUserDto);
    }

    @PostMapping("/validate-token")
    public ResponseEntity<?> validateToken(HttpServletRequest request) {
        return new ResponseEntity<>(HttpStatus.OK);
//        String authHeader = request.getHeader("Authorization");
//        String bearerString = "Bearer ";
//        String jwt, username;
//        if (authHeader != null && authHeader.startsWith(bearerString)) {
//            System.out.println("if1");
//            jwt = authHeader.substring(bearerString.length());
//                try {
//                    username = jwtUtil.extractUsername(jwt);
//                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                        System.out.println("if2");
//                        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
//                                username, null, jwtUtil.extractRoles(jwt).stream().map(SimpleGrantedAuthority::new).toList()
//                        );
//                        SecurityContextHolder.getContext().setAuthentication(token);
//                    }
//                    return new ResponseEntity<>(HttpStatus.OK);
//                }
//                catch (ExpiredJwtException e) {
//                    log.error("Время жизни токена истекло.");
//                    throw new RuntimeException(e.getMessage());
//                }
//                catch (SignatureException e) {
//                    log.error("Подпись JWT не совпадает с локально вычисленной подписью. Действительность JWT не может быть подтверждена и не должна вызывать доверия.");
//                    throw new SignatureException(e.getMessage());
//                }
//        }
//        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}
