package sidmeyer.l2shop.core.controller.dtohelpers;

import sidmeyer.l2shop.core.model.User;
import sidmeyer.l2shop.dto.UserDto;

public class UserDtoHelper {

    public static UserDto userToDto(final User user) {
        UserDto dto = new UserDto();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setActive(user.isActive());
        dto.setAdmin(user.isAdmin());

        return dto;
    }

    public static User userFromDto(final UserDto dto) {
        User user = new User();

        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setAdmin(dto.isAdmin());
        user.setActive(dto.isActive());

        return user;
    }
}
