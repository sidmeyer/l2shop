package sidmeyer.l2shop.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sidmeyer.l2shop.commons.OrderStatus;
import sidmeyer.l2shop.core.exceptions.OrderNotFoundException;
import sidmeyer.l2shop.core.exceptions.ProductNotInStockException;
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
import java.util.stream.Collectors;

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
	private IProductsService productsService;

	@Autowired
	private IUsersService usersService;

	@Override
	@Transactional
	public Order createOrder(final Order order) {
		final Order createdOrder = ordersDao.save(order);
		order.getProductInOrder()
				.forEach(pio -> {
					pio.setOrder(order);
					productsInOrdersDao.save(pio);
				});

		removeFromStock(order.getProductInOrder());

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
	@Transactional
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
	@Transactional
    public Order updateOrderAdmin(final Order order) {
        Optional<Order> existingOrderOptional = ordersDao.findById(order.getId());
        if (!existingOrderOptional.isPresent()) {
			throw new OrderNotFoundException("Order with id " + order.getId() + " does not exist.");
		}
        Order existingOrder = existingOrderOptional.get();

        // prevent updating some fields
        order.setCreatedDate(order.getCreatedDate());
        order.setUser(existingOrder.getUser());

		updateProductInStockIfNeeded(existingOrder, order);

		Order updatedOrder = ordersDao.save(order);

		return updatedOrder;
	}

	private void updateProductInStockIfNeeded(Order oldOrder, Order newOrder) {
		if (!needCheckProductInStockDifferences(oldOrder.getStatus(), newOrder.getStatus())) {
			return;
		}

		if (newOrder.getStatus() == OrderStatus.CANCELLED) {
			returnToStock(newOrder.getProductInOrder());
		}

		List<ProductInOrder> deletedFromOrder = oldOrder.getProductInOrder().stream()
				.filter(opio -> newOrder.getProductInOrder().stream().noneMatch(npio -> npio.getProduct().getId() == opio.getProduct().getId()))
				.collect(Collectors.toList());

		List<ProductInOrder> addedToOrder = newOrder.getProductInOrder().stream()
				.filter(npio -> oldOrder.getProductInOrder().stream().noneMatch(opio -> opio.getProduct().getId() == npio.getProduct().getId()))
				.collect(Collectors.toList());

		removeFromStock(addedToOrder);
		returnToStock(deletedFromOrder);

		// process changed product quantities
		newOrder.getProductInOrder().stream()
				.filter(npio -> oldOrder.getProductInOrder().stream().anyMatch(opio -> opio.getProduct().getId() == npio.getProduct().getId() && opio.getQuantity() != npio.getQuantity()))
				.forEach(npio -> {
					long productId = npio.getProduct().getId();
					int oldQuantity = oldOrder.getProductInOrder().stream().filter(opio -> opio.getProduct().getId() == productId).findAny().get().getQuantity();
					int newQuantity = npio.getQuantity();

					if (newQuantity > oldQuantity) {
						removeProductFromStock(productId, newQuantity - oldQuantity);
					} else {
						returnProductToStock(productId, oldQuantity - newQuantity);
					}
				});
	}

	private void removeFromStock(final List<ProductInOrder> productsInOrder) {
		productsInOrder.forEach(pio -> {
			removeProductFromStock(pio.getProduct().getId(), pio.getQuantity());
		});
	}

	private void removeProductFromStock(final long productId, int quantity) {
		Product product = productsService.getProduct(productId);
		int inStock = product.getInStock();

		if (inStock < quantity) {
			throw new ProductNotInStockException("There are not enough items of product {}. In stock {}, needed {}", product.getName(), inStock, quantity);
		}

		product.setInStock(inStock - quantity);
		productsService.updateProduct(product);
	}

	private void returnToStock(final List<ProductInOrder> productsInOrder) {
		productsInOrder.forEach(pio -> {
			returnProductToStock(pio.getProduct().getId(), pio.getQuantity());
		});
	}

	private void returnProductToStock(final long productId, final int quantity) {
		Product product = productsService.getProduct(productId);
		int inStock = product.getInStock();
		product.setInStock(inStock + quantity);
		productsService.updateProduct(product);
	}

	private boolean needCheckProductInStockDifferences(final OrderStatus oldStatus, final OrderStatus newStatus) {
		return newStatus == OrderStatus.CANCELLED && oldStatus != OrderStatus.CANCELLED;
	}

	@PostConstruct
	private void init() {

		// create test order

		User user = usersService.getUsers().get(1);

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
