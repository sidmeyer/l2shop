package sidmeyer.l2shop.core.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sidmeyer.l2shop.core.model.Order;

import java.util.List;

/**
 * Created by Stas on 14.08.2018.
 */
@Repository
public interface OrdersDao extends CrudRepository<Order, Long> {

    List<Order> findByUserId(final long userId);

}
