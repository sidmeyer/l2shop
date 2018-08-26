package sidmeyer.l2shop.front.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sidmeyer.l2shop.api.Api;
import sidmeyer.l2shop.dto.CategoryDto;
import sidmeyer.l2shop.dto.ProductDto;
import sidmeyer.l2shop.front.commons.Settings;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Stas on 18.08.2018.
 */
@Service
public class ProductsService {

	private RestTemplate restTemplate = new RestTemplate();

	public ProductDto getProduct(final long productId) {
        ProductDto product = restTemplate.getForObject(Settings.BACKEND_URL + Api.Products.PRODUCTS + "/" + productId, ProductDto.class);
		return product;
	}

	public Set<ProductDto> getProducts() {
        Set<ProductDto> products = restTemplate.getForObject(Settings.BACKEND_URL + Api.Products.PRODUCTS, Set.class);
		return products;
	}

	public void deleteProduct(final long productId) {
        restTemplate.delete(Settings.BACKEND_URL + Api.Products.PRODUCTS + "/" + productId);
	}

	public void editProduct(final ProductDto product) {
        restTemplate.put(Settings.BACKEND_URL + Api.Products.PRODUCTS + "/" + product.getId(), product);
	}

	public Set<CategoryDto> getProductCategories(final long productId) {
        CategoryDto[] categories = restTemplate.getForObject(Settings.BACKEND_URL + Api.Products.PRODUCTS_CATEGORIES.replace("{productId}", String.valueOf(productId)), CategoryDto[].class);
		return Arrays.stream(categories).collect(Collectors.toSet());
	}

	public void setProductCategories(final long productId, Set<CategoryDto> categories) {
        restTemplate.postForObject(Settings.BACKEND_URL + Api.Products.PRODUCTS_CATEGORIES.replace("{productId}", String.valueOf(productId)), categories, String.class);
	}

	public Set<CategoryDto> getAllCategories() {
        CategoryDto[] categories = restTemplate.getForObject(Settings.BACKEND_URL + Api.Categories.CATEGORIES, CategoryDto[].class);
		return Arrays.stream(categories).collect(Collectors.toSet());
	}

	public void addCategoryToProduct(final long productId, final long categoryId) {
        restTemplate.postForObject(Settings.BACKEND_URL + Api.Products.PRODUCTS_CATEGORIES_ID.replace("{productId}", String.valueOf(productId)).replace("{categoryId}", String.valueOf(categoryId)), null, String.class);
	}

	public void deleteCategoryFromProduct(final long productId, final long categoryId) {
        restTemplate.delete(Settings.BACKEND_URL + Api.Products.PRODUCTS_CATEGORIES_ID.replace("{productId}", String.valueOf(productId)).replace("{categoryId}", String.valueOf(categoryId)), null, String.class);
	}

	public long createProduct(final ProductDto product) {
        final long productId = restTemplate.postForObject(Settings.BACKEND_URL + Api.Products.PRODUCTS, product, Long.class);
		return productId;
	}

}
