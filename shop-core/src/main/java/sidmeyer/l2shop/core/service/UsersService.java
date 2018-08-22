package sidmeyer.l2shop.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sidmeyer.l2shop.core.model.User;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by Stas on 15.08.2018.
 */
@Service
public class UsersService {

	@Autowired
	private UsersDao usersDao;

	public long createUser(final User user) {
		return usersDao.save(user).getId();
	}

	public List<User> getUsers() {
		return (List<User>) usersDao.findAll();
	}

	public void updateUser(final User user) {
		usersDao.save(user);
	}

	public void deleteUser(long userId) {
		usersDao.deleteById(userId);
	}

	public User getUser(final long userId) {
		return usersDao.findById(userId).get();
	}

	@PostConstruct
	private void init() {

		// create test users
		User admin = new User();
		admin.setAdmin(true);
		admin.setName("admin1");
		usersDao.save(admin);

		User user = new User();
		user.setAdmin(false);
		user.setName("user1");
		usersDao.save(user);

		System.err.println("TEST USERS CREATED");
	}
}
