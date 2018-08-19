package sidmeyer.l2shop.dto;

import sidmeyer.l2shop.commons.OrderStatus;

import java.util.Date;
import java.util.Set;

/**
 * Created by Stas on 17.08.2018.
 */
public class OrderDto {
	private long id;
	private Set<ProductInOrderDto> productsInOrders;
	private long userId;
	private String deliveryAddress;
	private OrderStatus status;
	private Date createdDate;
	private Date finishedDate;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Set<ProductInOrderDto> getProductsInOrders() {
		return productsInOrders;
	}

	public void setProductsInOrders(Set<ProductInOrderDto> productsInOrders) {
		this.productsInOrders = productsInOrders;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getFinishedDate() {
		return finishedDate;
	}

	public void setFinishedDate(Date finishedDate) {
		this.finishedDate = finishedDate;
	}
}
