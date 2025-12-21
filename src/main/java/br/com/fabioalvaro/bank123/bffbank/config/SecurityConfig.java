package br.com.fabioalvaro.bank123.bffbank.config;

import br.com.fabioalvaro.bank123.bffbank.security.FirebaseTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final FirebaseTokenFilter firebaseTokenFilter;

    public SecurityConfig(FirebaseTokenFilter firebaseTokenFilter) {
        this.firebaseTokenFilter = firebaseTokenFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // API REST não precisa de CSRF
            .authorizeHttpRequests(auth -> auth
                // Libera o actuator (health check)
                .requestMatchers("/actuator/**").permitAll()
                // Libera o Swagger UI e os docs da API
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                // Libera o webhook de onboarding
                .requestMatchers("/onboarding/v1/webhook-firebase-add").permitAll()
                // Libera o webhook de onboarding
                .requestMatchers("/comandos/**").permitAll()
                // Opcional: Liberar OPTIONS para CORS se o front estiver em outro domínio
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // TODAS as outras requisições precisam de autenticação
                .anyRequest().authenticated()
            )
            // Insere nosso filtro ANTES do filtro padrão do Spring
            .addFilterBefore(firebaseTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}