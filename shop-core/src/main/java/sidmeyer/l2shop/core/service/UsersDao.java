package sidmeyer.l2shop.core.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sidmeyer.l2shop.core.model.User;

/**
 * Created by Stas on 15.08.2018.
 */
@Repository
public interface UsersDao extends CrudRepository<User, Long> {
}
