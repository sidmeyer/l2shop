package sidmeyer.l2shop.core.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sidmeyer.l2shop.api.Api;
import sidmeyer.l2shop.core.controller.dtohelpers.UserDtoHelper;
import sidmeyer.l2shop.core.security.MyAuthentication;
import sidmeyer.l2shop.dto.UserDto;

@RestController
@RequestMapping(Api.USER)
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
public class UsersUserController {

    @RequestMapping(path = Api.Users.USERS, method = RequestMethod.GET)
    public ResponseEntity<UserDto> getUser(MyAuthentication auth) {
        UserDto userDto = UserDtoHelper.userToDto(auth.getUser());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
