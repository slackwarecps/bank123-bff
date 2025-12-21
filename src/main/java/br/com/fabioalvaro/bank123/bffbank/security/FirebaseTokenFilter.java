package br.com.fabioalvaro.bank123.bffbank.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FirebaseTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        // Se não tem header ou não começa com Bearer, segue o fluxo (o SecurityConfig vai barrar depois)
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7); // Remove o "Bearer "

        try {
            // Valida o token no Firebase
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            String uid = decodedToken.getUid();
            String email = decodedToken.getEmail();
            
            // Extrai claims/scopes
            List<GrantedAuthority> authorities = new ArrayList<>();
            Object scopeClaim = decodedToken.getClaims().get("scope");
            if (scopeClaim instanceof String) {
                String[] scopes = ((String) scopeClaim).split(" ");
                authorities = Arrays.stream(scopes)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
            }

            // Cria a identidade do usuário no Spring Security
            var auth = new UsernamePasswordAuthenticationToken(email, uid, authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (Exception e) {
            // Se o token for inválido, expirado ou falso, limpamos o contexto
            SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token Firebase Inválido: " + e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }
}