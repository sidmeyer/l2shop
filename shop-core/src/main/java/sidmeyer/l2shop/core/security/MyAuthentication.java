package sidmeyer.l2shop.core.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import sidmeyer.l2shop.core.model.User;

import java.util.Collection;

public class MyAuthentication extends UsernamePasswordAuthenticationToken {

    private final User user;

    public MyAuthentication(Object principal, Object credentials, final User user) {
        super(principal, credentials);
        this.user = user;
    }

    public MyAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, final User user) {
        super(principal, credentials, authorities);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
