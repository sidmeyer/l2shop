package sidmeyer.l2shop.core.model;

import sidmeyer.l2shop.commons.OrderStatus;
import sidmeyer.l2shop.dto.OrderDto;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Stas on 14.08.2018.
 */
@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToMany(mappedBy = "order")
	private Set<ProductInOrder> productInOrder;

	@ManyToOne
	private User user;

	private String deliveryAddress;

	private OrderStatus status;

	private Date createdDate;

	private Date finishedDate;

	public Order(/*long id, */Set<ProductInOrder> productInOrder, User user) {
//		this.id = id;
		this.productInOrder = productInOrder;
		this.user = user;
	}

	public Order() {
	}

	public static Order createFromDto(final OrderDto dto) {
		Order order = new Order();
		order.setId(dto.getId());
		order.setDeliveryAddress(dto.getDeliveryAddress());
		order.setStatus(dto.getStatus());
		order.setCreatedDate(dto.getCreatedDate());
		order.setFinishedDate(dto.getFinishedDate());
		return order;
	}

	public OrderDto toDto() {
		OrderDto dto = new OrderDto();
		dto.setId(id);
		dto.setUserId(user.getId());
		dto.setDeliveryAddress(deliveryAddress);
		dto.setStatus(status);
		dto.setCreatedDate(createdDate);
		dto.setFinishedDate(finishedDate);
		return dto;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Set<ProductInOrder> getProductInOrder() {
		return productInOrder;
	}

	public void setProductInOrder(Set<ProductInOrder> productInOrder) {
		this.productInOrder = productInOrder;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
