package sidmeyer.l2shop.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sidmeyer.l2shop.api.Api;
import sidmeyer.l2shop.core.controller.dtohelpers.OrderDtoHelper;
import sidmeyer.l2shop.core.exceptions.OrderNotFoundException;
import sidmeyer.l2shop.core.exceptions.http.NotFoundException;
import sidmeyer.l2shop.core.model.Order;
import sidmeyer.l2shop.core.service.IOrdersService;
import sidmeyer.l2shop.dto.OrderDto;

@RestController
@RequestMapping(Api.ADMIN)
public class OrdersAdminController {

    @Autowired
    IOrdersService ordersService;

    @RequestMapping(path = Api.Orders.ORDERS_ID, method = RequestMethod.PUT)
    public ResponseEntity<Order> updateOrder(@PathVariable long orderId, @RequestBody OrderDto orderDto) {
        final Order order = OrderDtoHelper.orderFromDto(orderDto);
        order.setId(orderId);
        try {
            ordersService.updateOrderAdmin(order);
        } catch (OrderNotFoundException e) {
            throw new NotFoundException("Order with id %s does not exist.", orderId);
        }
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = Api.Orders.ORDERS_ID, method = RequestMethod.DELETE)
    public ResponseEntity<Order> deleteOrder(@PathVariable long orderId) {
        try {
            Order order = ordersService.getOrder(orderId);
            order.setDeleted(true);
            ordersService.updateOrderAdmin(order);
        } catch (OrderNotFoundException e) {
            throw new NotFoundException("Order with id %s does not exist.", orderId);
        }
        return ResponseEntity.noContent().build();
    }
}
