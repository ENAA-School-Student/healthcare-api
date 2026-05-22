package com.healthcare.medicalsystem.configuration;

import com.healthcare.medicalsystem.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

     @Bean
     public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
             throws Exception {
         return config.getAuthenticationManager();
     }

     @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                 // Public
                 .requestMatchers("/api/auth/**").permitAll()

                 // ADMIN seulement
                 .requestMatchers(HttpMethod.DELETE, "/api/patients/**").hasRole("ADMIN")
                 .requestMatchers(HttpMethod.DELETE, "/api/medecins/**").hasRole("ADMIN")
                 .requestMatchers("/api/users/**").hasRole("ADMIN")

                 // ADMIN + MEDECIN
                 .requestMatchers("/api/dossiers/**").hasAnyRole("ADMIN","MEDECIN")

                 // ADMIN + MEDECIN + PATIENT (lecture de son propre profil)
                 .requestMatchers(HttpMethod.GET, "/api/rendezvous/**")
                 .hasAnyRole("ADMIN","MEDECIN","PATIENT")
                 .requestMatchers(HttpMethod.GET, "/api/patients/**")
                 .hasAnyRole("ADMIN","MEDECIN","PATIENT")

                 // Tout le reste : authentifié
                 .anyRequest().authenticated())
                .addFilterBefore(new JwtFilter(customUserDetailsService, jwtUtils), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
