package uz.qodirov.config;

import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.qodirov.revoke_access_token.RevokeAccessTokenService;
import uz.qodirov.user.UserEntity;
import uz.qodirov.user.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    private final TokenUtil tokenUtil;
    private final UserService userService;
    private final RevokeAccessTokenService revokeAccessTokenService;

    public AuthenticationFilter(TokenUtil tokenUtil, UserService userService, RevokeAccessTokenService revokeAccessTokenService) {
        this.tokenUtil = tokenUtil;
        this.userService = userService;
        this.revokeAccessTokenService = revokeAccessTokenService;
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (tokenUtil.validateToken(token) && !revokeAccessTokenService.isRevoked(token)) {
                String userId = tokenUtil.getUsernameFromToken(token);
                UserEntity userEntity;
                try {
                    userEntity = userService.findById(userId);
                } catch (Exception e) {
                    throw new AccessDeniedException("User not found");
                }
                UserDetailsImpl userDetails = new UserDetailsImpl(userEntity);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new AccessDeniedException("Token expired!");
            }
        }
        chain.doFilter(request, response);
    }
}