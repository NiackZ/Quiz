package quiz.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
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
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER_STR = "Bearer ";
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(AUTHORIZATION);
        String username = null;
        String jwt = null;
        if (StringUtils.hasText(authHeader) && authHeader.startsWith(BEARER_STR)) {
            try {
                jwt = authHeader.substring(BEARER_STR.length());
                if (!jwt.equalsIgnoreCase("null")) {
                    username = jwtUtil.extractUsername(jwt);
                }
            }
            catch (ExpiredJwtException e) {
                log.error("Время жизни токена истекло.");
            }
            catch (SignatureException e) {
                log.error("Подпись JWT не совпадает с локально вычисленной подписью. Действительность JWT не может быть подтверждена и не должна вызывать доверия.");
            }
            catch (MalformedJwtException exception) {
                log.error("MalformedJwtException: ");
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
