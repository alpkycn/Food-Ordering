package com.essensbestellung.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.essensbestellung.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final String AUTHENTICATE = "/rest/api/auth/authenticate";
    public static final String REGISTER = "/rest/api/auth/register";

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationFilter authenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Explizite Deaktivierung von CSRF
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS-Konfiguration
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(AUTHENTICATE, REGISTER).permitAll()
                .requestMatchers("/rest/api/user/save").hasRole("ADMIN")
                .requestMatchers("/rest/api/user/list/{id}").hasRole("ADMIN")
                .requestMatchers("/rest/api/user/list").hasRole("ADMIN")
                .requestMatchers("/rest/api/user/delete/{id}").hasRole("ADMIN")
                .requestMatchers("/rest/api/order/update/{id}").hasAnyRole("KUECHENPERSONAL","GRUPPENLEITER") 
                .requestMatchers("/rest/api/order/list/{id}").hasAnyRole("ADMIN","KUECHENPERSONAL") 
                .requestMatchers("/rest/api/order/list").hasAnyRole("ADMIN","KUECHENPERSONAL")
                .requestMatchers("/rest/api/order/upgrade").hasRole("KUECHENPERSONAL")
                .requestMatchers("/rest/api/order/save").hasRole("GRUPPENLEITER") 
                .requestMatchers("/rest/api/order/save-multiple").hasRole("GRUPPENLEITER")
                .requestMatchers("/rest/api/location/list/{id}").hasRole("KUNDE")
                .requestMatchers("/rest/api/location/list").hasRole("ADMIN")
                .requestMatchers("/rest/api/gruppen/list").hasAnyRole("STANDORTLEITER","GRUPPENLEITER","ADMIN")
                .requestMatchers("/rest/api/gruppenmitglieder/list").hasRole("GRUPPENLEITER")
                .requestMatchers("/rest/api/gruppen/save").hasAnyRole("ADMIN")
                .requestMatchers("/rest/api/gruppen/{groupId}/members").hasAnyRole("ADMIN","GRUPPENLEITER")
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:8081"); // Ursprung hinzufÃ¼gen
        configuration.addAllowedOrigin("http://172.26.92.152");
        configuration.addAllowedMethod("*"); // Alle Methoden erlauben
        configuration.addAllowedHeader("*"); // Alle Header erlauben
        configuration.setAllowCredentials(true); // Cookies/Anmeldedaten erlauben
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
