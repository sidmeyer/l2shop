package sidmeyer.l2shop.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sidmeyer.l2shop.core.exceptions.UserNotFoundException;
import sidmeyer.l2shop.core.model.User;
import sidmeyer.l2shop.core.repository.UsersDao;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by Stas on 15.08.2018.
 */
@Service
public class UsersService implements IUsersService {

	@Autowired
	private UsersDao usersDao;

	@Override
	public User createUser(final User user) {
		return usersDao.save(user);
	}

	@Override
	public List<User> getUsers() {
		return (List<User>) usersDao.findAll();
	}

	@Override
	public User updateUser(final User user) {
		if (!usersDao.findById(user.getId()).isPresent()) {
			throw new UserNotFoundException("User with id " + user.getId() + " does not exist.");
		}
		return usersDao.save(user);
	}

	@Override
	public void deleteUser(final long userId) {
		usersDao.deleteById(userId);
	}

	@Override
	public User getUser(final long userId) {
		return usersDao.findById(userId).get();
	}

	@PostConstruct
	private void init() {

		// create test users
		User admin = new User();
		admin.setAdmin(true);
        admin.setName("admin");
        admin.setEmail("a@a.a");
        admin.setPassword("$2a$04$0bFhduHgyPjLd/vNglfcq.9khw5EJ/GRxejJT8HXP9P/ysZ5Mk29G");
        admin.setActive(true);
        admin.setAdmin(true);
		usersDao.save(admin);

		User user = new User();
		user.setAdmin(false);
        user.setName("user");
        user.setEmail("u@u.u");
        user.setPassword("$2a$04$q66Fuh1MrRhqthvBwo4NXeJOTjaOuWKV66dPqyuUR/hgi17zPoGte");
        user.setActive(true);
        user.setAdmin(false);
		usersDao.save(user);

		System.err.println("TEST USERS CREATED");
	}
}
