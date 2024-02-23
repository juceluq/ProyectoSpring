

package proyecto_spring.security;

import proyecto_spring.security.filter.JwtAuthenticationFilter;
import proyecto_spring.security.filter.JwtValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/users/register").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/libros", "/api/libros/{id}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST, "/api/libros").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/libros/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/libros/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/prestamos").hasAnyRole("ADMIN", "USER") //PREGUNTAR HACER QUE SOLO VEA EL PRESTAMO QUE TENGA
                        .requestMatchers(HttpMethod.POST, "/api/prestamos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/prestamos/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/prestamos/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/reservas").hasAnyRole("ADMIN", "USER") //PREGUNTAR HACER QUE SOLO VEA LA RESERVA QUE TENGA
                        .requestMatchers(HttpMethod.POST, "/api/reservas").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/reservas/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/reservas/{id}").hasRole("ADMIN")
                .anyRequest().authenticated())
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtValidationFilter(authenticationManager()))
                .csrf(config -> config.disable())
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();

    }

}
