package sidmeyer.l2shop.core.service;

import sidmeyer.l2shop.core.model.User;

import java.util.List;

public interface IUsersService {

    User createUser(final User user);

    User updateUser(final User user);

    void deleteUser(final long userId);

    User getUser(final long userId);

    List<User> getUsers();
}
