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
import ua.foxminded.pskn.universitycms.security.MyAuthenticationSuccessHandler;
import ua.foxminded.pskn.universitycms.security.MyUserDetailsService;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final MyUserDetailsService myUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/", "/home").permitAll()
                .requestMatchers("/studentscab").hasRole("student")
                .requestMatchers("/professorscab").hasRole("professor")
                .requestMatchers("/adminscab", "/welcome").hasRole("admin")

                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .defaultSuccessUrl("/cabinet")
                .successHandler(myAuthenticationSuccessHandler())
                .loginPage("/login")
                .permitAll()
            )
            .authenticationProvider(authenticationProvider())
            .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(cryptPasswordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MyAuthenticationSuccessHandler();
    }

    @Bean
    public BCryptPasswordEncoder cryptPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }
}
