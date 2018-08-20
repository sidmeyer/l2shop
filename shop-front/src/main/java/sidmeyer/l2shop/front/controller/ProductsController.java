package sidmeyer.l2shop.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import sidmeyer.l2shop.dto.CategoryDto;
import sidmeyer.l2shop.dto.ProductDto;
import sidmeyer.l2shop.front.service.ProductsService;

import java.util.*;

/**
 * Created by Stas on 17.08.2018.
 */
@Controller
public class ProductsController {

	@Autowired
	private ProductsService productsService;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/products/list")
	public String productsList(Model model) {

		Set<ProductDto> products = productsService.getProducts();

		model.addAttribute("products", products);
		return "products/productslist";
	}

	@GetMapping("/products/details")
	public String productDetails(Model model, @RequestParam(name = "product") long productId) {
		ProductDto productDto = productsService.getProduct(productId);
		model.addAttribute("product", productDto);
		Set<CategoryDto> categories = productsService.getProductCategories(productId);
		model.addAttribute("categories", categories);
		return "products/productdetails";
	}

	@GetMapping("/products/management")
	public String productsManagement(Model model) {
		Set<ProductDto> products = productsService.getProducts();
		model.addAttribute("products", products);
		return "products/productsmanagement";
	}

	@GetMapping("/products/edit")
	public String editProduct(Model model, @RequestParam(name = "product") long productId) {
		ProductDto productDto = productsService.getProduct(productId);
		model.addAttribute("product", productDto);
		Set<CategoryDto> categories = productsService.getProductCategories(productId);
		model.addAttribute("categories", categories);

		Set<CategoryDto> otherCategories = productsService.getAllCategories();
		for (CategoryDto cat : categories) {
			otherCategories.remove(cat);
		}
		model.addAttribute("otherCategories", otherCategories);

		return "/products/productedit";
	}

	@PostMapping("/products/edit")
	public RedirectView submitEditProduct(ProductDto product, BindingResult bindingResult, Model model) {
		Set<CategoryDto> categories = productsService.getProductCategories(product.getId());
		productsService.editProduct(product);
		productsService.setProductCategories(product.getId(), categories);
		return new RedirectView("/products/edit?product=" + product.getId());
	}

	@GetMapping("/products/delete")
	public RedirectView deleteProduct(Model model, @RequestParam(name = "product") long productId) {
		productsService.deleteProduct(productId);
		return new RedirectView("/products/management");
	}

	@PostMapping("/products/categories/edit")
	public RedirectView submitEditProductCategories(Set<CategoryDto> categories, BindingResult bindingResult, Model model, @RequestParam(name = "product") long productId) {
		productsService.setProductCategories(productId, categories);
		return new RedirectView("/products/edit?product=" + productId);
	}

	@GetMapping("/products/addcategory")
	public RedirectView addCategoryToProduct(Model model, @RequestParam(name = "product") long productId, @RequestParam(name = "category") long categoryId) {
		productsService.addCategoryToProduct(productId, categoryId);
		return new RedirectView("/products/edit?product=" + productId);
	}

	@GetMapping("/products/deletecategory")
	public RedirectView deleteCategoryFromProduct(Model model, @RequestParam(name = "product") long productId, @RequestParam(name = "category") long categoryId) {
		productsService.deleteCategoryFromProduct(productId, categoryId);
		return new RedirectView("/products/edit?product=" + productId);
	}

	@GetMapping("/products/create")
	public String createProduct(Model model) {
		model.addAttribute("product", new ProductDto());
		return "/products/productcreate";
	}

	@PostMapping("/products/create")
	public RedirectView submitCreateProduct(ProductDto product, BindingResult bindingResult, Model model) {
		final long productId = productsService.createProduct(product);
		return new RedirectView("/products/edit?product=" + productId);
	}
}
