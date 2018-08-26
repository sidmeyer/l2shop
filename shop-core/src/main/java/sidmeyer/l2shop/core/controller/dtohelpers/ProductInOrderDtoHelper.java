package sidmeyer.l2shop.core.controller.dtohelpers;

import sidmeyer.l2shop.core.model.Order;
import sidmeyer.l2shop.core.model.ProductInOrder;
import sidmeyer.l2shop.dto.ProductInOrderDto;

public class ProductInOrderDtoHelper {

    public static ProductInOrderDto productInOrderToDto(final ProductInOrder productInOrder) {
        ProductInOrderDto dto = new ProductInOrderDto();

        dto.setId(productInOrder.getId());
        dto.setProduct(ProductDtoHelper.productToDto(productInOrder.getProduct()));
        dto.setOrderId(productInOrder.getOrder().getId());
        dto.setQuantity(productInOrder.getQuantity());
        dto.setOriginalPrice(productInOrder.getOriginalPrice());

        return dto;
    }

    public static ProductInOrder productInOrderFromDto(final ProductInOrderDto dto) {
        ProductInOrder productInOrder = new ProductInOrder();

        productInOrder.setId(dto.getId());
        productInOrder.setProduct(ProductDtoHelper.productFromDto(dto.getProduct()));

        Order order = new Order();
        order.setId(dto.getOrderId());
        productInOrder.setOrder(order);

        productInOrder.setOriginalPrice(dto.getOriginalPrice());
        productInOrder.setQuantity(dto.getQuantity());

        return productInOrder;
    }
}
