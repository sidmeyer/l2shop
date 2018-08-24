package sidmeyer.l2shop.core.model;

import sidmeyer.l2shop.dto.ProductDto;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Stas on 14.08.2018.
 */
@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name;

	private double price;

	private String imageUrl;

	@ManyToMany
	private List<Category> categories;

	@OneToMany(mappedBy = "product")
	private List<ProductInOrder> productInOrder;

	public Product(long id, String name, double price, String imageUrl/*, Set<ProductInOrder> productInOrder*/) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.imageUrl = imageUrl;
//		this.productInOrder = productInOrder;
	}

	public Product() {
	}

	public static Product createFromDto(final ProductDto dto) {
		Product product = new Product();
		product.setId(dto.getId());
		product.setName(dto.getName());
		product.setImageUrl(dto.getImageUrl());
		product.setPrice(dto.getPrice());
		return product;
	}

	public ProductDto toDto() {
		ProductDto dto = new ProductDto();
		dto.setId(id);
		dto.setName(name);
		dto.setPrice(price);
		dto.setImageUrl(imageUrl);
		return dto;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	//@OneToMany(targetEntity = sidmeyer.l2shop.core.commons.Product.class, mappedBy = "product")
//	public Set<ProductInOrder> getProductInOrder() {
//		return productInOrder;
//	}
//
//	public void setProductInOrder(Set<ProductInOrder> productsInOrder) {
//		this.productInOrder = productsInOrder;
//	}


	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
}
