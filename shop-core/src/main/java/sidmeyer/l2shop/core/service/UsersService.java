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
		admin.setName("admin1");
		usersDao.save(admin);

		User user = new User();
		user.setAdmin(false);
		user.setName("user1");
		usersDao.save(user);

		System.err.println("TEST USERS CREATED");
	}
}
