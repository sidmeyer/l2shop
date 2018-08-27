package sidmeyer.l2shop.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sidmeyer.l2shop.api.Api;
import sidmeyer.l2shop.core.controller.dtohelpers.OrderDtoHelper;
import sidmeyer.l2shop.core.exceptions.OrderNotFoundException;
import sidmeyer.l2shop.core.exceptions.http.NotFoundException;
import sidmeyer.l2shop.core.model.Order;
import sidmeyer.l2shop.core.service.IOrdersService;
import sidmeyer.l2shop.dto.OrderDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(Api.ADMIN)
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
public class OrdersAdminController {

    @Autowired
    IOrdersService ordersService;

    @RequestMapping(path = Api.Orders.ORDERS, method = RequestMethod.GET)
    public ResponseEntity<List<OrderDto>> getOrders() {
        List<OrderDto> orderDtos = ordersService.getOrders()
                .stream()
                .map(OrderDtoHelper::orderToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(orderDtos, HttpStatus.OK);
    }

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
