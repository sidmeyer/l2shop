package sidmeyer.l2shop.core.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sidmeyer.l2shop.core.model.User;

import java.util.Collection;
import java.util.Collections;

public class MyUserPrincipal implements UserDetails {

    private final User user;

    public MyUserPrincipal(final User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        String role = user.isAdmin() ? "ROLE_ADMIN" : "ROLE_USER";

        GrantedAuthority grantedAuthority = () -> role;

        return Collections.singletonList(grantedAuthority);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isActive();
    }

    public User getUser() {
        return user;
    }
}
