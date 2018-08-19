package sidmeyer.l2shop.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sidmeyer.l2shop.api.Api;
import sidmeyer.l2shop.core.model.*;
import sidmeyer.l2shop.core.service.OrdersService;
import sidmeyer.l2shop.dto.OrderDto;
import sidmeyer.l2shop.dto.ProductInOrderDto;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Stas on 14.08.2018.
 */
@RestController
public class OrdersController {

	@Autowired
	private OrdersService ordersService;

	@RequestMapping(path = Api.Orders.ORDERS_PATH, method = RequestMethod.POST)
	public long createOrder(@RequestBody OrderDto orderDto) {
		Order order = createOrderFromDto(orderDto);

		return ordersService.createOrder(order);
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
