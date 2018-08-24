package sidmeyer.l2shop.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sidmeyer.l2shop.api.Api;
import sidmeyer.l2shop.core.exceptions.UserNotFoundException;
import sidmeyer.l2shop.core.model.User;
import sidmeyer.l2shop.core.service.IUsersService;

import java.net.URI;
import java.util.List;

/**
 * Created by Stas on 15.08.2018.
 */
@RestController
public class UsersController {

	@Autowired
	private IUsersService usersService;

	@RequestMapping(path = Api.Users.USERS_PATH, method = RequestMethod.GET)
	public List<User> getUsers() {
		return usersService.getUsers();
	}

	@RequestMapping(path = Api.Users.USERS_PATH, method = RequestMethod.POST)
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User createdUser = usersService.createUser(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@RequestMapping(path = Api.Users.USERS_ID_PATH, method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@PathVariable long userId, @RequestBody User user) {
		user.setId(userId);
		try {
			usersService.updateUser(user);
		} catch (UserNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(path = Api.Users.USERS_ID_PATH, method = RequestMethod.DELETE)
	public long deleteUser(@PathVariable long userId) {
		usersService.deleteUser(userId);
		return userId;
	}

	@RequestMapping(path = Api.Users.USERS_ID_PATH, method = RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable long userId) {
		User user = usersService.getUser(userId);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
}
