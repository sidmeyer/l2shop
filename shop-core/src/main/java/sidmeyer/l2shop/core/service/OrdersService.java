package sidmeyer.l2shop.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sidmeyer.l2shop.core.controller.OrdersDao;
import sidmeyer.l2shop.core.controller.ProductsInOrdersDao;
import sidmeyer.l2shop.core.model.*;
import sidmeyer.l2shop.commons.OrderStatus;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by Stas on 14.08.2018.
 */
@Service
public class OrdersService {

	@Autowired
	private OrdersDao ordersDao;

	@Autowired
	private ProductsInOrdersDao productsInOrdersDao;

	@Autowired
	private ProductsService productsService;

	@Autowired
	private UsersService usersService;

	public long createOrder(Order order) {
		long orderId = ordersDao.save(order).getId();
		order.getProductInOrder()
				.forEach(pio -> {
					pio.setOrder(order);
					productsInOrdersDao.save(pio);
				});
		return orderId;
	}

	public List<Order> getOrders() {
		return (List<Order>) ordersDao.findAll();
	}

	public Order getOrder(final long orderId) {
		return ordersDao.findById(orderId).get();
	}

	public void deleteOrder(final long orderId) {
		ordersDao.deleteById(orderId);
	}

	@PostConstruct
	private void init() {

		// create test order

		User user = usersService.getUsers().get(0);

		Order order = new Order();
		order.setUser(user);
		order.setDeliveryAddress("Yavornyts'koho str.");
		order.setCreatedDate(new Date());
		order.setStatus(OrderStatus.NEW);

		ordersDao.save(order);

		//ordersDao.save(order);

		Product product = productsService.getProducts().get(0);

		ProductInOrder productInOrder = new ProductInOrder();
		productInOrder.setOrder(order);
		productInOrder.setProduct(product);
		productInOrder.setQuantity(1);

		productsInOrdersDao.save(productInOrder);

		Set<ProductInOrder> productsInOrders = new HashSet<>();
		productsInOrders.add(productInOrder);

		order.setProductInOrder(productsInOrders);

		ordersDao.save(order);

		System.err.println("TEST ORDER CREATED");
	}
}
