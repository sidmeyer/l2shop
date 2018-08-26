package sidmeyer.l2shop.core.security;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public class MyDaoAuthenticationProvider extends DaoAuthenticationProvider {

    @Override
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {

        MyAuthentication result = new MyAuthentication(principal, authentication.getCredentials(), user.getAuthorities(), ((MyUserPrincipal) user).getUser());
        result.setDetails(authentication.getDetails());
        return result;
    }
}
