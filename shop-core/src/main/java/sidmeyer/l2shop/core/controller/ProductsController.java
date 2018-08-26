package sidmeyer.l2shop.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sidmeyer.l2shop.api.Api;
import sidmeyer.l2shop.core.controller.dtohelpers.CategoryDtoHelper;
import sidmeyer.l2shop.core.controller.dtohelpers.ProductDtoHelper;
import sidmeyer.l2shop.core.exceptions.ProductNotFoundException;
import sidmeyer.l2shop.core.exceptions.http.NotFoundException;
import sidmeyer.l2shop.core.model.Category;
import sidmeyer.l2shop.core.model.Product;
import sidmeyer.l2shop.core.service.IProductsService;
import sidmeyer.l2shop.dto.CategoryDto;
import sidmeyer.l2shop.dto.ProductDto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Stas on 16.08.2018.
 */
@RestController
@RequestMapping(Api.ROOT)
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
public class ProductsController {

    @Autowired
    private IProductsService productsService;

    @RequestMapping(path = Api.Products.PRODUCTS, method = RequestMethod.GET)
    public List<ProductDto> getProducts(@RequestParam(name = "shownotinstock", required = false, defaultValue = "false") boolean showNotInStock,
                                        @RequestParam(name = "category", required = false, defaultValue = "0") long categoryId) {
        return productsService.getProducts()
                .stream()
                .filter(product -> product.getInStock() > 0 || showNotInStock)
                .filter(product -> categoryId < 1 || product.getCategories().stream().anyMatch(category -> category.getId() == categoryId))
                .map(ProductDtoHelper::productToDto)
                .collect(Collectors.toList());
    }

    @RequestMapping(path = Api.Products.PRODUCTS_ID, method = RequestMethod.GET)
    public ResponseEntity<ProductDto> getProduct(@PathVariable long productId) {
        final ProductDto productDto;
        try {
            Product product = productsService.getProduct(productId);
            productDto = ProductDtoHelper.productToDto(product);
        } catch (ProductNotFoundException e) {
            throw new NotFoundException("Product with ID {} does not exist.", productId);
        }
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @RequestMapping(path = Api.Products.PRODUCTS_CATEGORIES, method = RequestMethod.GET)
    public List<CategoryDto> getProductCategories(@PathVariable long productId) {
        return productsService.getProduct(productId)
                .getCategories().stream()
                .map(Category::toDto)
                .collect(Collectors.toList());
    }


    @RequestMapping(path = Api.Categories.CATEGORIES, method = RequestMethod.GET)
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = productsService.getAllCategories()
                .stream()
                .map(CategoryDtoHelper::categoryToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
