package sidmeyer.l2shop.core.service;

import sidmeyer.l2shop.core.model.Order;

import java.util.List;

public interface IOrdersService {

    Order createOrder(final Order order);

    Order updateOrder(final Order order);

    Order getOrder(final long orderId);

    List<Order> getOrders();

    void deleteOrder(final long orderId);
}
