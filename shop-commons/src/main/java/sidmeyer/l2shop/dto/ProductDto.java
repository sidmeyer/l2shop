package sidmeyer.l2shop.dto;

/**
 * Created by Stas on 17.08.2018.
 */
public class ProductDto {
	private long id;
	private String name;
	private String imageUrl;
	private double price;

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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "ProductDto{" +
				"id=" + id +
				", name='" + name + '\'' +
				", imageUrl='" + imageUrl + '\'' +
				", price=" + price +
				'}';
	}
}
