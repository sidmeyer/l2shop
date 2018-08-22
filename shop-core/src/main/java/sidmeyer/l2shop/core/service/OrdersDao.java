package sidmeyer.l2shop.core.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import sidmeyer.l2shop.core.model.Order;
import sidmeyer.l2shop.core.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Stas on 14.08.2018.
 */
@Repository
public interface OrdersDao extends CrudRepository<Order, Long> {

//	public long addOrder(Order order) {
//		return 1;
//	}
//
//	public List<Order> getOrders() {
//		return new ArrayList<>(Arrays.asList(new Order(1, new ArrayList<>(), new User(1, "Vasia", false))));
//	}
//
//	public Order getOrder(final long orderId) {
//		return new Order(orderId, new ArrayList<>(), new User(1, "Vasia", false));
//	}

}
