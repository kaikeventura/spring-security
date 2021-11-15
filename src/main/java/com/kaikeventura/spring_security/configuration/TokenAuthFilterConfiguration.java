package com.kaikeventura.spring_security.configuration;

import com.kaikeventura.spring_security.repository.UserRepository;
import com.kaikeventura.spring_security.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class TokenAuthFilterConfiguration extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        var token = this.getTokenFromHeader(request);
        var tokenIsValid = this.tokenService.isTokenValid(token);

        if(tokenIsValid) {
            this.authenticate(token);
        }

        filterChain.doFilter(request, response);
    }

    private void authenticate(String tokenFromHeader) {
        var email = this.tokenService.getTokenId(tokenFromHeader);

        this.userRepository.findByEmail(email).ifPresent(user -> {
            var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        });
    }

    private String getTokenFromHeader(HttpServletRequest request) {
        var token = request.getHeader("Authorization");
        if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }

        return token.substring(7);
    }
}
