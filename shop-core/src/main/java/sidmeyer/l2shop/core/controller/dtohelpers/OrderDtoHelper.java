package sidmeyer.l2shop.core.controller.dtohelpers;

import sidmeyer.l2shop.core.model.Order;
import sidmeyer.l2shop.core.model.ProductInOrder;
import sidmeyer.l2shop.core.model.User;
import sidmeyer.l2shop.dto.OrderDto;
import sidmeyer.l2shop.dto.ProductInOrderDto;

import java.util.List;
import java.util.stream.Collectors;

public class OrderDtoHelper {

    public static OrderDto orderToDto(final Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setUserId(order.getUser().getId());
        dto.setCreatedDate(order.getCreatedDate());
        dto.setFinishedDate(order.getFinishedDate());
        dto.setDeliveryAddress(order.getDeliveryAddress());
        dto.setStatus(order.getStatus());

        List<ProductInOrderDto> productInOrderDtos = order.getProductInOrder().stream()
                .map(ProductInOrderDtoHelper::productInOrderToDto)
                .collect(Collectors.toList());
        dto.setProductsInOrder(productInOrderDtos);

        return dto;
    }

    public static Order orderFromDto(final OrderDto dto) {
        Order order = new Order();
        order.setId(dto.getId());
        order.setDeliveryAddress(dto.getDeliveryAddress());
        order.setStatus(dto.getStatus());
        order.setCreatedDate(dto.getCreatedDate());
        order.setFinishedDate(dto.getFinishedDate());

        User user = new User();
        user.setId(dto.getUserId());
        order.setUser(user);

        List<ProductInOrder> productsInOrder = dto.getProductsInOrder().stream()
                .map(ProductInOrderDtoHelper::productInOrderFromDto)
                .collect(Collectors.toList());
        order.setProductInOrder(productsInOrder);
        return order;
    }

}
