package sidmeyer.l2shop.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sidmeyer.l2shop.api.Api;
import sidmeyer.l2shop.core.controller.dtohelpers.UserDtoHelper;
import sidmeyer.l2shop.core.exceptions.UserNotFoundException;
import sidmeyer.l2shop.core.model.User;
import sidmeyer.l2shop.core.service.IUsersService;
import sidmeyer.l2shop.dto.UserDto;

import java.net.URI;
import java.util.List;

/**
 * Created by Stas on 15.08.2018.
 */
@RestController
@RequestMapping(Api.ADMIN)
public class UsersAdminController {

	@Autowired
	private IUsersService usersService;

    @RequestMapping(path = Api.Users.USERS, method = RequestMethod.GET)
	public List<User> getUsers() {
		return usersService.getUsers();
	}

    @RequestMapping(path = Api.Users.USERS, method = RequestMethod.POST)
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        User user = UserDtoHelper.userFromDto(userDto);
		User createdUser = usersService.createUser(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

    @RequestMapping(path = Api.Users.USERS_ID, method = RequestMethod.PUT)
    public ResponseEntity<UserDto> updateUser(@PathVariable long userId, @RequestBody UserDto userDto) {
        User user = UserDtoHelper.userFromDto(userDto);
        userDto.setId(userId);
		try {
			usersService.updateUser(user);
		} catch (UserNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}

    @RequestMapping(path = Api.Users.USERS_ID, method = RequestMethod.DELETE)
    public ResponseEntity<UserDto> deleteUser(@PathVariable long userId) {
		User user = usersService.getUser(userId);
        user.setActive(false);
        usersService.updateUser(user);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = Api.Users.USERS_ID, method = RequestMethod.GET)
    public ResponseEntity<UserDto> getUser(@PathVariable long userId) {
        User user = usersService.getUser(userId);
        UserDto userDto = UserDtoHelper.userToDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
	}
}
