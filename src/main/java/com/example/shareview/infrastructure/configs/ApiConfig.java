package com.example.shareview.infrastructure.configs;

import com.example.shareview.infrastructure.input.api.security.CustomAccessDeniedHandler;
import com.example.shareview.infrastructure.input.api.security.CustomAuthenticationEntryPoint;
import com.example.shareview.infrastructure.input.api.services.UserDetailsServiceImpl;
import enums.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Profile("api")
public class ApiConfig {

    private static final String[] WHITE_LIST_URL = { "/api/v1/auth/**", "/v2/api-docs", "/v3/api-docs",
            "/v3/api-docs/**", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
            "/configuration/security", "/swagger-ui/**", "/webjars/**", "/swagger-ui.html", "/api/auth/**",
            "/api/test/**", "/api/v1/usuarios/aluno" };

    private static final String[] STUDENT_LIST_URL = { "/api/v1/feedbacks" };

    private static final String[] ADMIN_LIST_URL = { "/api/v1/cursos", "/api/v1/cursos/*", "/api/v1/turmas", "/api/v1/turmas/*",
            "/adiciona-aluno-em-turma/*/*", "/adiciona-professor-em-turma/*/*", "/remove-aluno-de-turma/*/*", "/remove-professor-de-turma/*/*" };

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) {
        http.httpBasic(Customizer.withDefaults());
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(WHITE_LIST_URL).permitAll()
                                .requestMatchers(STUDENT_LIST_URL).hasAuthority(UserType.STUDENT.name())
                                .requestMatchers(HttpMethod.POST, "/api/v1/usuarios").hasAuthority(UserType.ADMIN.name())
                                .requestMatchers(ADMIN_LIST_URL).hasAuthority(UserType.ADMIN.name())
                                .anyRequest().authenticated())
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler))
                .oauth2ResourceServer(
                        conf -> conf.jwt(Customizer.withDefaults()));
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}
