package sidmeyer.l2shop.dto;

import java.util.Objects;

/**
 * Created by Stas on 19.08.2018.
 */
public class CategoryDto {
	private long id;
	private String name;
	private long parentId;

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

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CategoryDto that = (CategoryDto) o;
		return Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {

		return Objects.hash(name);
	}
}
