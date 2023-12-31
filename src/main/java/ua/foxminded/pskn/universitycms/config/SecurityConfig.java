package ua.foxminded.pskn.universitycms.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import ua.foxminded.pskn.universitycms.security.SecureAuthenticationSuccessHandler;
import ua.foxminded.pskn.universitycms.security.SecureUserDetailsService;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final SecureUserDetailsService secureUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/", "/home").permitAll()

            )
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/adminscab").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/studentscab").hasAuthority("ROLE_STUDENT")
                .requestMatchers("/professorscab").hasAuthority("ROLE_PROFESSOR")
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .successHandler(myAuthenticationSuccessHandler())
                .loginPage("/login")
                .permitAll()
            )
            .authenticationProvider(authenticationProvider())
            .logout((logout) -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(secureUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(cryptPasswordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new SecureAuthenticationSuccessHandler();
    }

    @Bean
    public BCryptPasswordEncoder cryptPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }
}
