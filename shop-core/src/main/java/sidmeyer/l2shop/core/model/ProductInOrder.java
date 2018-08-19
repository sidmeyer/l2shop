package sidmeyer.l2shop.core.model;

import sidmeyer.l2shop.dto.ProductInOrderDto;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Stas on 14.08.2018.
 */
@Entity
@Table(name = "products_in_orders")
public class ProductInOrder implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne
	//@JoinColumn(name = "product_id")
	private Product product;

	@ManyToOne
	//@JoinColumn(name = "order_id")
	private Order order;

	private int quantity;

	public ProductInOrder(/*long id, */Product product, Order order, int quantity) {
//		this.id = id;
		this.product = product;
		this.order = order;
		this.quantity = quantity;
	}

	public ProductInOrder() {
	}

	public ProductInOrderDto toDto() {
		ProductInOrderDto dto = new ProductInOrderDto();
		dto.setId(id);
		dto.setProduct(product.toDto());
		dto.setOrderId(order.getId());
		dto.setQuantity(quantity);
		return dto;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
