

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
                .requestMatchers(HttpMethod.GET, "/api/libros/disponible").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/api/libros/autor").hasAnyRole("ADMIN", "USER")




                .requestMatchers(HttpMethod.GET, "/api/prestamos").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST, "/api/prestamos").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/prestamos/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/prestamos/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/prestamos/por-libro").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/api/prestamos/por-usuario").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/api/prestamos/atrasados").hasAnyRole("ADMIN", "USER")





                .requestMatchers(HttpMethod.GET, "/api/reservas").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST, "/api/reservas").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/reservas/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/reservas/{id}").hasRole("ADMIN")





                .requestMatchers(HttpMethod.GET, "/api/actividades").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST, "/api/actividades").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.PUT, "/api/actividades/{id}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.DELETE, "/api/actividades/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/actividades/fecha").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/api/actividades/descripcion").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/api/actividades/usuario/{usuarioId}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/api/actividades/usuario-fecha").hasAnyRole("ADMIN", "USER")



                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**","/swagger-ui.html").permitAll()


                .anyRequest().authenticated())
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtValidationFilter(authenticationManager()))
                .csrf(config -> config.disable())
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();

    }

}
