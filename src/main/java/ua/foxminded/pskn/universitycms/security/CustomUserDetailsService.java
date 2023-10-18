package ua.foxminded.pskn.universitycms.security;

import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUserDetailsService extends User {

    private final Long userId;

    public CustomUserDetailsService(final String username,
                                    final String password,
                                    final Collection<? extends GrantedAuthority> authorities,
                                    final Long userId) {
        super(username, password, authorities);
        this.userId = userId;
    }

    public Long getUserId(){
        return userId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        final CustomUserDetailsService that = (CustomUserDetailsService) o;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userId);
    }
}
