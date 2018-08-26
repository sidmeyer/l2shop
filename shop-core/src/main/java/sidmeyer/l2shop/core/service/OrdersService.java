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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public List<Order> getOrdersByUser(final long userId) {
        return ordersDao.findByUserId(userId);
    }

	@Override
	public Order getOrder(final long orderId) {
        Optional<Order> orderOptional = ordersDao.findById(orderId);
        if (!orderOptional.isPresent()) {
            throw new OrderNotFoundException("Order with id " + orderId + " does not exist.");
        }
        return orderOptional.get();
	}

	@Override
	public void deleteOrder(final long orderId) {
		ordersDao.deleteById(orderId);
	}

	@Override
    public Order updateOrderUser(final Order order) {
        Optional<Order> existingOrderOptional = ordersDao.findById(order.getId());
        if (!existingOrderOptional.isPresent()) {
            throw new OrderNotFoundException("Order with id " + order.getId() + " does not exist.");
        }
        Order existingOrder = existingOrderOptional.get();

        // prevent updating some fields
        order.setCreatedDate(order.getCreatedDate());
        order.setUser(existingOrder.getUser());
        order.setDeleted(existingOrder.isDeleted());

        return ordersDao.save(order);
    }

    @Override
    public Order updateOrderAdmin(final Order order) {
        Optional<Order> existingOrderOptional = ordersDao.findById(order.getId());
        if (!existingOrderOptional.isPresent()) {
			throw new OrderNotFoundException("Order with id " + order.getId() + " does not exist.");
		}
        Order existingOrder = existingOrderOptional.get();

        // prevent updating some fields
        order.setCreatedDate(order.getCreatedDate());
        order.setUser(existingOrder.getUser());

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

        List<ProductInOrder> productsInOrders = new ArrayList<>();
		productsInOrders.add(productInOrder);

		order.setProductInOrder(productsInOrders);

		ordersDao.save(order);

		System.err.println("TEST ORDER CREATED");
	}
}
