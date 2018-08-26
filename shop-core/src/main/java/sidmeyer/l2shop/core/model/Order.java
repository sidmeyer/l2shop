package sidmeyer.l2shop.core.model;

import org.hibernate.annotations.CreationTimestamp;
import sidmeyer.l2shop.commons.OrderStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
    private List<ProductInOrder> productInOrder;

	@ManyToOne
	private User user;

	private String deliveryAddress;

	private OrderStatus status;

    @CreationTimestamp
    private Date createdDate;

	private Date finishedDate;

    private boolean isDeleted;

	public Order() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

    public List<ProductInOrder> getProductInOrder() {
		return productInOrder;
	}

    public void setProductInOrder(List<ProductInOrder> productInOrder) {
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
