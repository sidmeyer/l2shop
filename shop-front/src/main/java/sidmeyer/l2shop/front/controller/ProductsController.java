package sidmeyer.l2shop.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sidmeyer.l2shop.dto.CategoryDto;
import sidmeyer.l2shop.dto.ProductDto;
import sidmeyer.l2shop.front.service.ProductsService;

import java.util.Set;

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
	public String productDetails(Model model, @RequestParam(name = "productid") long productId) {
		ProductDto productDto = productsService.getProduct(productId);
		model.addAttribute("product", productDto);
		Set<CategoryDto> categories = productsService.getCategoriesForProduct(productId);
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
	public String editProduct(Model model, @RequestParam(name = "productid") long productId) {
		ProductDto productDto = productsService.getProduct(productId);
		model.addAttribute("product", productDto);
		Set<CategoryDto> categories = productsService.getCategoriesForProduct(productId);
		model.addAttribute("categories", categories);
		return "products/productedit";
	}

	@PostMapping("/products/edit")
	public String submitEditProduct(ProductDto product, BindingResult bindingResult, Model model) {
		productsService.editProduct(product);
		model.addAttribute("product", product);
		Set<CategoryDto> categories = productsService.getCategoriesForProduct(product.getId());
		model.addAttribute("categories", categories);
		return "products/productedit";
	}

	@GetMapping("/products/delete")
	public String deleteProduct(Model model, @RequestParam(name = "productid") long productId) {
		productsService.deleteProduct(productId);
		model.addAttribute("productId", productId);
		return "products/productdelete";
	}
}
