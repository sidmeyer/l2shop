package sidmeyer.l2shop.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sidmeyer.l2shop.commons.OrderStatus;
import sidmeyer.l2shop.core.exceptions.OrderNotFoundException;
import sidmeyer.l2shop.core.model.Order;
import sidmeyer.l2shop.core.model.Product;
import sidmeyer.l2shop.core.model.ProductInOrder;
import sidmeyer.l2shop.core.model.User;
import sidmeyer.l2shop.core.repository.OrdersDao;
import sidmeyer.l2shop.core.repository.ProductsInOrdersDao;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Stas on 14.08.2018.
 */
@Service
public class OrdersService implements IOrdersService {

	@Autowired
	private OrdersDao ordersDao;

	@Autowired
	private ProductsInOrdersDao productsInOrdersDao;

	@Autowired
	private ProductsService productsService;

	@Autowired
	private UsersService usersService;

	@Override
	public Order createOrder(final Order order) {
		final Order createdOrder = ordersDao.save(order);
		order.getProductInOrder()
				.forEach(pio -> {
					pio.setOrder(order);
					productsInOrdersDao.save(pio);
				});
		return createdOrder;
	}

	@Override
	public List<Order> getOrders() {
		return (List<Order>) ordersDao.findAll();
	}

	@Override
	public Order getOrder(final long orderId) {
		return ordersDao.findById(orderId).get();
	}

	@Override
	public void deleteOrder(final long orderId) {
		ordersDao.deleteById(orderId);
	}

	@Override
	public Order updateOrder(final Order order) {
		if (!ordersDao.findById(order.getId()).isPresent()) {
			throw new OrderNotFoundException("Order with id " + order.getId() + " does not exist.");
		}
		return ordersDao.save(order);
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
