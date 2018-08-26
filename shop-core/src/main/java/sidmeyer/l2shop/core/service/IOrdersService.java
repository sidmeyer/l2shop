package sidmeyer.l2shop.core.service;

import sidmeyer.l2shop.core.model.Order;

import java.util.List;

public interface IOrdersService {

    Order createOrder(final Order order);

    Order updateOrderUser(final Order order);

    Order updateOrderAdmin(final Order order);

    Order getOrder(final long orderId);

    List<Order> getOrders();

    List<Order> getOrdersByUser(final long userId);

    void deleteOrder(final long orderId);
}
