package sidmeyer.l2shop.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sidmeyer.l2shop.api.Api;
import sidmeyer.l2shop.core.exceptions.ProductNotFoundException;
import sidmeyer.l2shop.core.model.Category;
import sidmeyer.l2shop.core.model.Product;
import sidmeyer.l2shop.core.service.IProductsService;
import sidmeyer.l2shop.dto.CategoryDto;
import sidmeyer.l2shop.dto.ProductDto;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Stas on 16.08.2018.
 */
@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
public class ProductsController {

	@Autowired
	private IProductsService productsService;

	@RequestMapping(path = Api.Products.PRODUCTS_PATH, method = RequestMethod.POST)
	public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
		final Product createdProduct = productsService.createProduct(Product.createFromDto(productDto));

		final URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdProduct.getId()).toUri();

		return ResponseEntity.created(location).build();

	}

	@RequestMapping(path = Api.Products.PRODUCTS_PATH, method = RequestMethod.GET)
	public List<ProductDto> getProducts() {
		return productsService.getProducts()
				.stream()
				.map(Product::toDto)
				.collect(Collectors.toList());
	}

	@RequestMapping(path = Api.Products.PRODUCTS_ID_PATH, method = RequestMethod.GET)
	public ResponseEntity<ProductDto> getProduct(@PathVariable long productId) {
		final ProductDto productDto = productsService.getProduct(productId).toDto();
		return new ResponseEntity<>(productDto, HttpStatus.OK);
	}

	@RequestMapping(path = Api.Products.PRODUCTS_ID_PATH, method = RequestMethod.DELETE)
	public String deleteProduct(@PathVariable long productId) {
		productsService.deleteProduct(productId);
		return "Product with id " + productId + " was deleted.";
	}

	@RequestMapping(path = Api.Products.PRODUCTS_ID_PATH, method = RequestMethod.PUT)
	public ResponseEntity<ProductDto> updateProduct(@PathVariable long productId, @RequestBody ProductDto productDto) {
		final Product product = Product.createFromDto(productDto);
		try {
			productsService.updateProduct(product);
		} catch (ProductNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
		productsService.updateProduct(product);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(path = Api.Products.PRODUCTS_CATEGORIES_PATH, method = RequestMethod.GET)
	public List<CategoryDto> getProductCategories(@PathVariable long productId) {
		return productsService.getProduct(productId)
				.getCategories().stream()
				.map(Category::toDto)
				.collect(Collectors.toList());
//		return productsService.getCategoriesForProduct(productId)
//				.stream()
//				.map(Category::toDto)
//				.collect(Collectors.toList());
	}

	@RequestMapping(path = Api.Products.PRODUCTS_CATEGORIES_PATH, method = RequestMethod.POST)
	public String updateProductCategories(@PathVariable long productId, @RequestBody List<CategoryDto> categoryDtos) {
		List<Category> categories = categoryDtos
				.stream()
				.map(Category::createFromDto)
				.collect(Collectors.toList());
		final Product product = productsService.getProduct(productId);
		product.setCategories(categories);
		productsService.updateProduct(product);
		return "Categories for product with id " + productId + " was updated";
	}

	@RequestMapping(path = Api.Categories.CATEGORIES_PATH, method = RequestMethod.GET)
	public List<CategoryDto> getAllCategories() {
		return productsService.getAllCategories()
				.stream()
				.map(Category::toDto)
				.collect(Collectors.toList());
	}

	@RequestMapping(path = Api.Products.PRODUCTS_CATEGORIES_ID_PATH, method = RequestMethod.POST)
	public String addCategoryToProduct(@PathVariable long productId, @PathVariable long categoryId) {
		productsService.addCategoryToProduct(productId, categoryId);
		return "Category with ID " + categoryId + " has been added to product with ID " + productId;
	}

	@RequestMapping(path = Api.Products.PRODUCTS_CATEGORIES_ID_PATH, method = RequestMethod.DELETE)
	public String deleteCategoryFromProduct(@PathVariable long productId, @PathVariable long categoryId) {
		productsService.deleteCategoryFromProduct(productId, categoryId);
		return "Category with ID " + categoryId + " has been deleted from product with ID " + productId;
	}
}
