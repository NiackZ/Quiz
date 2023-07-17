package quiz.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import quiz.utils.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtRequestFilter.class);
    private static final String AUTHORIZATION = "Authorization";
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(AUTHORIZATION);
        String username = null;
        String jwt = null;
        String bearerString = "Bearer ";
        if (StringUtils.hasText(authHeader) && authHeader.startsWith(bearerString)) {
            try {
                jwt = authHeader.substring(bearerString.length());
                username = jwtUtil.extractUsername(jwt);
            }
            catch (ExpiredJwtException e) {
                log.error("Время жизни токена истекло.");
                throw new RuntimeException(e.getMessage());
            }
            catch (SignatureException e) {
                log.error("Подпись JWT не совпадает с локально вычисленной подписью. Действительность JWT не может быть подтверждена и не должна вызывать доверия.");
                throw new SignatureException(e.getMessage());
            }
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    username, null, jwtUtil.extractRoles(jwt).stream().map(SimpleGrantedAuthority::new).toList()
            );
            SecurityContextHolder.getContext().setAuthentication(token);
        }
        filterChain.doFilter(request, response);
    }

}
