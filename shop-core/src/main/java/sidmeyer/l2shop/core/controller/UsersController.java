package sidmeyer.l2shop.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sidmeyer.l2shop.api.Api;
import sidmeyer.l2shop.core.model.User;
import sidmeyer.l2shop.core.service.UsersService;

import java.util.List;

/**
 * Created by Stas on 15.08.2018.
 */
@RestController
public class UsersController {

	@Autowired
	private UsersService usersService;

	@RequestMapping(path = Api.Users.USERS_PATH, method = RequestMethod.GET)
	public List<User> getUsers() {
		return usersService.getUsers();
	}

	@RequestMapping(path = Api.Users.USERS_PATH, method = RequestMethod.POST)
	public long createUser(@RequestBody User user) {
		return usersService.createUser(user);
	}

	@RequestMapping(path = Api.Users.USERS_ID_PATH, method = RequestMethod.PUT)
	public long updateUser(@PathVariable long userId, @RequestBody User user) {
		user.setId(userId);
		return userId;
	}

	@RequestMapping(path = Api.Users.USERS_ID_PATH, method = RequestMethod.DELETE)
	public long deleteUser(@PathVariable long userId) {
		usersService.deleteUser(userId);
		return userId;
	}

	@RequestMapping(path = Api.Users.USERS_ID_PATH, method = RequestMethod.GET)
	public User getUser(@PathVariable long userId) {
		return usersService.getUser(userId);
	}
}
