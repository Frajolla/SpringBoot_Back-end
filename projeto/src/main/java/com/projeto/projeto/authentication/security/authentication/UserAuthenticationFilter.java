package com.projeto.projeto.authentication.security.authentication;

import com.projeto.projeto.authentication.entitities.User;
import com.projeto.projeto.authentication.repositories.UserRepository;
import com.projeto.projeto.authentication.security.config.SecurityConfiguration;
import com.projeto.projeto.authentication.security.userdetails.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService; // Service que definimos anteriormente

    @Autowired
    private UserRepository userRepository; // Repository que definimos anteriormente

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Verifica se o endpoint requer autenticação antes de processar a requisição
        if (checkIfEndpointIsNotPublic(request)) {
            String token = recoveryToken(request); // Recupera o token do cabeçalho Authorization da requisição
            if (token != null) {
                String subject = jwtTokenService.getSubjectFromToken(token); // Obtém o assunto (neste caso, o nome de usuário) do token
                User user = userRepository.findByEmail(subject).get(); // Busca o usuário pelo email (que é o assunto do token)
                UserDetailsImpl userDetails = new UserDetailsImpl(user); // Cria um UserDetails com o usuário encontrado

                // Cria um objeto de autenticação do Spring Security
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());

                // Define o objeto de autenticação no contexto de segurança do Spring Security
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
                response.getWriter().write("O token esta ausente.");
                response.getWriter().flush();
            }
        }
        filterChain.doFilter(request, response); // Continua o processamento da requisição
    }

    // Recupera o token do cabeçalho Authorization da requisição
    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

    // Verifica se o endpoint requer autenticação antes de processar a requisição
    private boolean checkIfEndpointIsNotPublic(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return !Arrays.asList(SecurityConfiguration.ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).contains(requestURI)
                && !requestURI.startsWith("/swagger-ui")
                && !requestURI.startsWith("/v3/api-docs")
                && !requestURI.startsWith("/h2-console");
    }
}
