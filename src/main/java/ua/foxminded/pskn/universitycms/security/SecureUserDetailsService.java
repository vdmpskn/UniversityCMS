package ua.foxminded.pskn.universitycms.security;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.foxminded.pskn.universitycms.model.user.User;
import ua.foxminded.pskn.universitycms.service.user.UserService;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class SecureUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public SecureUserDetailsService(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userService.getUserByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Bad username!");
        }

        String roleName = "ROLE_" + user.get().getRole().getName();

        Collection<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(roleName));

        return new org.springframework.security.core.userdetails.User(
            user.get().getUsername(), user.get().getPassword(), authorities
        );
    }
}
