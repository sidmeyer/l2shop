package sidmeyer.l2shop.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sidmeyer.l2shop.core.model.User;
import sidmeyer.l2shop.core.repository.UsersDao;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UsersDao usersDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = usersDao.findByEmailIgnoreCase(email);

        if (user == null) {
            throw new UsernameNotFoundException("User with email " + email + " does not exist.");
        }

        return new MyUserPrincipal(user);
    }
}
