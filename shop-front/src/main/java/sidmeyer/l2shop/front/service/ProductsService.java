package sidmeyer.l2shop.front.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sidmeyer.l2shop.api.Api;
import sidmeyer.l2shop.dto.CategoryDto;
import sidmeyer.l2shop.dto.ProductDto;
import sidmeyer.l2shop.front.commons.Settings;

import java.util.Set;

/**
 * Created by Stas on 18.08.2018.
 */
@Service
public class ProductsService {

	private RestTemplate restTemplate = new RestTemplate();

	public ProductDto getProduct(final long productId) {
		//RestTemplate restTemplate = new RestTemplate();
		ProductDto product = restTemplate.getForObject(Settings.BACKEND_URL + Api.Products.PRODUCTS_PATH + "/" + productId, ProductDto.class);
		return product;
	}

	public Set<ProductDto> getProducts() {
		Set<ProductDto> products = restTemplate.getForObject(Settings.BACKEND_URL + Api.Products.PRODUCTS_PATH, Set.class);
		return products;
	}

	public void deleteProduct(final long productId) {
		restTemplate.delete(Settings.BACKEND_URL + Api.Products.PRODUCTS_PATH + "/" + productId);
	}

	public void editProduct(final ProductDto product) {
		restTemplate.put(Settings.BACKEND_URL + Api.Products.PRODUCTS_PATH + "/" + product.getId(), product);
	}

	public Set<CategoryDto> getCategoriesForProduct(final long productId) {
		Set<CategoryDto> categories = restTemplate.getForObject(Settings.BACKEND_URL + Api.Products.PRODUCTS_CATEGORIES_PATH.replace("{productId}", String.valueOf(productId)), Set.class);
		return categories;
	}

}
