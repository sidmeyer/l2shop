package sidmeyer.l2shop.dto;

import sidmeyer.l2shop.commons.OrderStatus;

import java.util.Date;
import java.util.List;

/**
 * Created by Stas on 17.08.2018.
 */
public class OrderDto {
	private long id;
	private List<ProductInOrderDto> productsInOrder;
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

	public List<ProductInOrderDto> getProductsInOrder() {
		return productsInOrder;
	}

	public void setProductsInOrder(List<ProductInOrderDto> productsInOrder) {
		this.productsInOrder = productsInOrder;
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
