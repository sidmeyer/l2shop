package sidmeyer.l2shop.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sidmeyer.l2shop.api.Api;
import sidmeyer.l2shop.commons.OrderStatus;
import sidmeyer.l2shop.core.controller.dtohelpers.OrderDtoHelper;
import sidmeyer.l2shop.core.exceptions.OrderNotFoundException;
import sidmeyer.l2shop.core.exceptions.http.NotFoundException;
import sidmeyer.l2shop.core.model.Order;
import sidmeyer.l2shop.core.model.Product;
import sidmeyer.l2shop.core.model.ProductInOrder;
import sidmeyer.l2shop.core.security.MyAuthentication;
import sidmeyer.l2shop.core.service.IOrdersService;
import sidmeyer.l2shop.core.service.IProductsService;
import sidmeyer.l2shop.dto.OrderDto;

import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(Api.USER)
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
public class OrdersUserController {

    private static final Logger LOG = LoggerFactory.getLogger(OrdersUserController.class);

    @Autowired
    IOrdersService ordersService;

    @Autowired
    IProductsService productsService;

    @RequestMapping(path = Api.Orders.ORDERS, method = RequestMethod.POST)
    public ResponseEntity<Order> createOrder(MyAuthentication auth, @RequestBody OrderDto orderDto) {
        final Order order = OrderDtoHelper.orderFromDto(orderDto);
        order.setStatus(OrderStatus.NEW);
        order.setUser(auth.getUser());
        setOriginalPrices(order.getProductInOrder());
        final Order createdOrder = ordersService.createOrder(order);

        final URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdOrder.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @RequestMapping(path = Api.Orders.ORDERS_ID, method = RequestMethod.PUT)
    public ResponseEntity<Order> updateOrder(MyAuthentication auth, @PathVariable long orderId, @RequestBody OrderDto orderDto) {
        final Order order = OrderDtoHelper.orderFromDto(orderDto);
        order.setId(orderId);
        order.setUser(auth.getUser());
        try {
            ordersService.updateOrderUser(order);
        } catch (OrderNotFoundException e) {
            throw new NotFoundException("Order with id %s does not exist.", orderId);
        }
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = Api.Orders.ORDERS, method = RequestMethod.GET)
    public Set<OrderDto> getOrders(MyAuthentication auth) {
        return ordersService.getOrdersByUser(auth.getUser().getId())
                .stream()
                .filter(o -> !o.isDeleted())
                .map(OrderDtoHelper::orderToDto)
                .collect(Collectors.toSet());
    }

    @RequestMapping(path = Api.Orders.ORDERS_ID, method = RequestMethod.GET)
    public ResponseEntity<OrderDto> getOrder(MyAuthentication auth, @PathVariable long orderId) {
        Order order;

        try {
            order = ordersService.getOrder(orderId);
        } catch (OrderNotFoundException e) {
            throw new NotFoundException("Order with id %s does not exist.", orderId);
        }

        if (order.isDeleted()) {
            throw new NotFoundException("Order with id %s does not exist.", orderId);
        }

        if (order.getUser().getId() != auth.getUser().getId()) {
            LOG.warn("Illegal access attempt. User ID {}, order ID {}, owner userID {}", auth.getUser().getId(), orderId, order.getUser().getId());
            throw new NotFoundException("Order with id %s does not exist.", orderId);
        }

        return new ResponseEntity<>(OrderDtoHelper.orderToDto(order), HttpStatus.OK);
    }

    @RequestMapping(path = Api.Orders.ORDERS_ID, method = RequestMethod.DELETE)
    public ResponseEntity<OrderDto> deleteOrder(MyAuthentication auth, @PathVariable long orderId) {
        Order order;

        try {
            order = ordersService.getOrder(orderId);
        } catch (OrderNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        if (order.getUser().getId() != auth.getUser().getId()) {
            LOG.warn("Illegal access attempt. User ID {}, order ID {}, owner userID {}", auth.getUser().getId(), orderId, order.getUser().getId());
            throw new NotFoundException("Order with id %s does not exist.", orderId);
        }

        if (order.getStatus() == OrderStatus.IN_PROGRESS) {
            throw new NotFoundException("Order with id %s does not exist.", orderId);
        }

        ordersService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    private void setOriginalPrices(final List<ProductInOrder> productsInOrder) {
        productsInOrder.forEach(productInOrder -> {
            final Product product = productsService.getProduct(productInOrder.getProduct().getId());
            productInOrder.setOriginalPrice(product.getPrice());
        });
    }
}
