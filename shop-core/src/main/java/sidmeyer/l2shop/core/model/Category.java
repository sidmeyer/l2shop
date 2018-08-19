package sidmeyer.l2shop.core.model;

import sidmeyer.l2shop.dto.CategoryDto;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Stas on 19.08.2018.
 */
@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name;

	@ManyToOne(targetEntity = Category.class)
	private Category parentCategory;

	@ManyToMany(mappedBy = "categories")
	private Set<Product> products;

	public static Category createFromDto(final CategoryDto dto) {
		Category category = new Category();
		category.setId(dto.getId());
		category.setName(dto.getName());
		if (dto.getParentId() > 0) {
			Category parent = new Category();
			parent.setId(dto.getParentId());
			category.setParentCategory(parent);
		}
		return category;
	}

	public CategoryDto toDto() {
		CategoryDto dto = new CategoryDto();
		dto.setId(id);
		dto.setName(name);
		if (parentCategory != null) {
			dto.setParentId(parentCategory.getId());
		}
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

	public Category getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}
}
