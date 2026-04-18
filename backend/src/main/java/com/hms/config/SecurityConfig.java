package com.hms.config;

import com.hms.security.CustomUserDetailsService;
import com.hms.security.RestAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    
    private final CustomUserDetailsService customUserDetailsService;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    
    public SecurityConfig(CustomUserDetailsService customUserDetailsService, 
                         RestAuthenticationEntryPoint restAuthenticationEntryPoint) {
        this.customUserDetailsService = customUserDetailsService;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
    }
    

    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint(restAuthenticationEntryPoint)
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(authorize -> authorize
                // Public routes
                .requestMatchers("/", "/login", "/login.html", "/register", "/index", "/index.html", "/register.html", "/forgot-password", "/reset-password").permitAll()
                .requestMatchers(HttpMethod.GET, "/css/**", "/js/**", "/images/**", "/fonts/**", "/hms/assets/**", "/hms/index.html").permitAll()
                
                // Specific Dashboard Protection (HTML files)
                .requestMatchers("/hms/dashboard-admin.html").hasRole("ADMIN")
                .requestMatchers("/hms/dashboard-doctor.html").hasRole("DOCTOR")
                .requestMatchers("/hms/dashboard-patient.html").hasRole("PATIENT")
                .requestMatchers("/hms/dashboard-nurse.html").hasRole("NURSE")
                
                // Content Pages Protection
                .requestMatchers("/hms/patients.html").hasAnyRole("ADMIN", "DOCTOR", "NURSE")
                .requestMatchers("/hms/appointments.html").hasAnyRole("ADMIN", "DOCTOR", "NURSE", "PATIENT")
                .requestMatchers("/hms/billing.html").hasAnyRole("ADMIN", "PATIENT")
                .requestMatchers("/hms/medical-records.html").hasAnyRole("ADMIN", "DOCTOR", "NURSE", "PATIENT")
                .requestMatchers("/hms/prescriptions.html").hasAnyRole("ADMIN", "DOCTOR", "NURSE", "PATIENT")
                .requestMatchers("/hms/admin-users.html").hasRole("ADMIN")

                // Admin API routes
                .requestMatchers("/hms/admin/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/hms/admin/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/hms/admin/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/hms/admin/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/hms/admin/users/**").hasRole("ADMIN")
                
                // Doctor/Clinical API routes
                .requestMatchers("/hms/doctor/**").hasRole("DOCTOR")
                .requestMatchers(HttpMethod.POST, "/hms/appointments/**").hasAnyRole("DOCTOR", "NURSE", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/hms/appointments/**").hasAnyRole("DOCTOR", "NURSE", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/hms/appointments/**").hasAnyRole("DOCTOR", "NURSE", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/hms/medical-records/**").hasRole("DOCTOR")
                .requestMatchers(HttpMethod.POST, "/hms/prescriptions/**").hasRole("DOCTOR")
                
                // Nurse API routes
                .requestMatchers("/hms/nurse/**").hasRole("NURSE")
                .requestMatchers(HttpMethod.PUT, "/hms/prescriptions/**").hasRole("NURSE")
                .requestMatchers(HttpMethod.PUT, "/hms/medical-records/**").hasRole("NURSE")
                
                // Patient API routes
                .requestMatchers("/hms/patient/**").hasRole("PATIENT")
                .requestMatchers(HttpMethod.GET, "/hms/patient/appointments/**").hasRole("PATIENT")
                .requestMatchers(HttpMethod.GET, "/hms/patient/medical-records/**").hasRole("PATIENT")
                .requestMatchers(HttpMethod.GET, "/hms/patient/bills/**").hasRole("PATIENT")
                
                // Generic API routes
                .requestMatchers("/hms/api/appointments/**").hasAnyRole("DOCTOR", "NURSE", "ADMIN", "PATIENT")
                .requestMatchers("/hms/api/medical-records/**").hasAnyRole("DOCTOR", "NURSE", "ADMIN", "PATIENT")
                .requestMatchers("/hms/api/prescriptions/**").hasAnyRole("DOCTOR", "NURSE", "ADMIN", "PATIENT")
                .requestMatchers("/hms/api/bills/**").hasAnyRole("ADMIN", "DOCTOR", "NURSE", "PATIENT")
                .requestMatchers("/hms/api/**").authenticated()
                
                // All other requests require authentication
                .anyRequest().authenticated()
            );
        
        return http.build();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("http://localhost:*", "http://localhost:3000", "http://localhost:8080"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}