package ua.foxminded.pskn.universitycms.security;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.foxminded.pskn.universitycms.model.user.User;
import ua.foxminded.pskn.universitycms.service.user.UserService;

import java.util.List;

@Service

public class SecureUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public SecureUserDetailsService(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Bad username!");
        }

        return new org.springframework.security.core.userdetails.User(
            user.getUsername(), user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRole()))
        );
    }
}
