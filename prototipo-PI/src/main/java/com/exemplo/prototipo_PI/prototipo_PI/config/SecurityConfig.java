package com.exemplo.prototipo_PI.prototipo_PI.config;

import com.exemplo.prototipo_PI.prototipo_PI.Filter.JwtRequestFilter;
import com.exemplo.prototipo_PI.prototipo_PI.Service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    // Password encoder bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Expose AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // DaoAuthenticationProvider to link UserDetailsService and PasswordEncoder
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // CORS configuration for frontend
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173", "http://localhost:5174"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // Security filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1) desabilita CSRF (já que vocês usam JWT)
                .csrf(csrf -> csrf.disable())

                // 2) permite carregar o console H2 em frames
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))

                // 3) habilita CORS usando a configuração acima
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 4) regras de autorização
                .authorizeHttpRequests(auth -> auth
                        // — libera acesso geral ao Swagger, H2 console e endpoints de autenticação
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/swagger-resources",
                                "/swagger-resources/**",
                                "/h2-console/**",
                                "/api/auth/**",
                                "/api/checkout/**"  // 🔥 ADICIONADO AQUI TAMBÉM
                        ).permitAll()

                        // — libera todos os métodos para /api/itens-pedidos
                        .requestMatchers("/api/itens-pedidos/**").permitAll()

                        // — libera endpoints de pagamento/checkout (redundante mas garante)
                        .requestMatchers(HttpMethod.GET, "/api/checkout/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/checkout/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/api/checkout/**").permitAll()

                        // — para qualquer outra requisição, exige autenticação
                        .anyRequest().authenticated()
                )

                // 5) usa gerenciamento de sessão stateless (já que estamos com JWT)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 6) registra o provider de autenticação via UserDetailsService
                .authenticationProvider(authenticationProvider());

        // 7) adiciona o filtro de JWT antes do UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
