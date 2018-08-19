package sidmeyer.l2shop.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sidmeyer.l2shop.api.Api;
import sidmeyer.l2shop.core.model.Category;
import sidmeyer.l2shop.core.model.Product;
import sidmeyer.l2shop.dto.CategoryDto;
import sidmeyer.l2shop.dto.ProductDto;
import sidmeyer.l2shop.core.service.ProductsService;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Stas on 16.08.2018.
 */
@RestController
public class ProductsController {

	@Autowired
	private ProductsService productsService;

	@RequestMapping(path = Api.Products.PRODUCTS_PATH, method = RequestMethod.POST)
	public long createProduct(@RequestBody ProductDto productDto) {
		return productsService.createProduct(Product.createFromDto(productDto));
	}

	@RequestMapping(path = Api.Products.PRODUCTS_PATH, method = RequestMethod.GET)
	public Set<ProductDto> getProducts() {
		return productsService.getProducts()
				.stream()
				.map(Product::toDto)
				.collect(Collectors.toSet());
	}

	@RequestMapping(path = Api.Products.PRODUCTS_ID_PATH, method = RequestMethod.GET)
	public ProductDto getProduct(@PathVariable long productId) {
		return productsService.getProduct(productId).toDto();
	}

	@RequestMapping(path = Api.Products.PRODUCTS_ID_PATH, method = RequestMethod.DELETE)
	public String deleteProduct(@PathVariable long productId) {
		productsService.deleteProduct(productId);
		return "Product with id " + productId + " was deleted.";
	}

	@RequestMapping(path = Api.Products.PRODUCTS_ID_PATH, method = RequestMethod.PUT)
	public String editProduct(@RequestBody ProductDto productDto) {
		productsService.editProduct(Product.createFromDto(productDto));
		return "Product with id " + productDto.getId() + " was updated.";
	}

	@RequestMapping(path = Api.Products.PRODUCTS_CATEGORIES_PATH, method = RequestMethod.GET)
	public Set<CategoryDto> getProductCategories(@PathVariable long productId) {
		return productsService.getCategoriesForProduct(productId)
				.stream()
				.map(Category::toDto)
				.collect(Collectors.toSet());
	}

	@RequestMapping(path = Api.Products.PRODUCTS_CATEGORIES_PATH, method = RequestMethod.POST)
	public String editProductCategories(@PathVariable long productId, @RequestBody Set<CategoryDto> categoryDtos) {
		Set<Category> categories = categoryDtos
				.stream()
				.map(Category::createFromDto)
				.collect(Collectors.toSet());
		productsService.editProductCategories(productId, categories);
		return "Categories for product with id " + productId + " was edited";
	}
}
