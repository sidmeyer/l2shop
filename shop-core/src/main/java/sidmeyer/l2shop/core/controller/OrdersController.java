package sidmeyer.l2shop.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sidmeyer.l2shop.api.Api;
import sidmeyer.l2shop.core.exceptions.OrderNotFoundException;
import sidmeyer.l2shop.core.model.Order;
import sidmeyer.l2shop.core.model.Product;
import sidmeyer.l2shop.core.model.ProductInOrder;
import sidmeyer.l2shop.core.model.User;
import sidmeyer.l2shop.core.service.IOrdersService;
import sidmeyer.l2shop.dto.OrderDto;
import sidmeyer.l2shop.dto.ProductInOrderDto;

import java.net.URI;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Stas on 14.08.2018.
 */
@RestController
public class OrdersController {

    @Autowired
    private IOrdersService ordersService;

    @RequestMapping(path = Api.Orders.ORDERS_PATH, method = RequestMethod.POST)
    public ResponseEntity<Order> createOrder(@RequestBody OrderDto orderDto) {
        final Order order = createOrderFromDto(orderDto);
        final Order createdOrder = ordersService.createOrder(order);

        final URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdOrder.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @RequestMapping(path = Api.Orders.ORDERS_ID_PATH, method = RequestMethod.PUT)
    public ResponseEntity<Order> updateOrder(@PathVariable long orderId, @RequestBody OrderDto orderDto) {
        final Order order = createOrderFromDto(orderDto);
        order.setId(orderId);
        try {
            ordersService.updateOrder(order);
        } catch (OrderNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = Api.Orders.ORDERS_PATH, method = RequestMethod.GET)
    public Set<OrderDto> getOrders() {
        return ordersService.getOrders()
                .stream()
                .map(Order::toDto)
                .collect(Collectors.toSet());
    }

    @RequestMapping(path = Api.Orders.ORDERS_ID_PATH, method = RequestMethod.GET)
    public OrderDto getOrder(@PathVariable long orderId) {
        Order order = ordersService.getOrder(orderId);

        return order.toDto();
    }

    @RequestMapping(path = Api.Orders.ORDERS_ID_PATH, method = RequestMethod.DELETE)
    public long deleteOrder(@PathVariable long orderId) {
        ordersService.deleteOrder(orderId);
        return orderId;
    }

    @RequestMapping(path = Api.Orders.PRODUCTS_IN_ORDER_PATH, method = RequestMethod.GET)
    public Set<ProductInOrderDto> getProductsInOrder(@PathVariable long orderId) {
        return ordersService.getOrder(orderId).getProductInOrder()
                .stream()
                .map(ProductInOrder::toDto)
                .collect(Collectors.toSet());
    }

    private Order createOrderFromDto(final OrderDto orderDto) {
        Order order = Order.createFromDto(orderDto);

        User user = new User();
        user.setId(orderDto.getUserId());
        order.setUser(user);

        Set<ProductInOrder> productsInOrders = orderDto.getProductsInOrders().stream()
                .map(piod -> {
                    ProductInOrder productInOrder = new ProductInOrder();
                    productInOrder.setProduct(Product.createFromDto(piod.getProduct()));
                    productInOrder.setQuantity(piod.getQuantity());
                    return productInOrder;
                })
                .collect(Collectors.toSet());

        order.setProductInOrder(productsInOrders);

        return order;
    }
}
