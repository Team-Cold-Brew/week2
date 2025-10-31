package com.mobilefix.mobilefix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    /**
     * Configuración del PasswordEncoder (BCrypt)
     * Este bean es necesario para encriptar passwords
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configuración de seguridad HTTP
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Configuración de autorización
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas (sin autenticación)
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()

                        // API REST - Endpoints protegidos por rol
                        // Orders
                        .requestMatchers("/api/orders").hasAnyRole("USER", "TECH", "ADMIN")
                        .requestMatchers("/api/orders/*/assign/*").hasRole("ADMIN")
                        .requestMatchers("/api/orders/*/status").hasAnyRole("TECH", "ADMIN")

                        // Devices
                        .requestMatchers("/api/devices").hasAnyRole("USER", "TECH", "ADMIN")
                        .requestMatchers("/api/devices/**").hasRole("ADMIN")

                        // Users (solo ADMIN)
                        .requestMatchers("/api/users/**").hasRole("ADMIN")

                        // Vistas JSP
                        .requestMatchers("/dashboard").authenticated()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/tech/**").hasRole("TECH")
                        .requestMatchers("/user/**").hasRole("USER")

                        // Cualquier otra ruta requiere autenticación
                        .anyRequest().authenticated()
                )

                // Configuración de login
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )

                // Configuración de logout
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )

                // Deshabilitar CSRF para H2 Console y APIs REST (solo en desarrollo)
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**", "/api/**")
                )

                // Permitir frames para H2 Console
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                );

        return http.build();
    }
}