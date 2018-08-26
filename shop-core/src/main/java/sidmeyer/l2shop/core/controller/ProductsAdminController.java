package sidmeyer.l2shop.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sidmeyer.l2shop.api.Api;
import sidmeyer.l2shop.core.controller.dtohelpers.ProductDtoHelper;
import sidmeyer.l2shop.core.exceptions.ProductNotFoundException;
import sidmeyer.l2shop.core.exceptions.http.NotFoundException;
import sidmeyer.l2shop.core.model.Category;
import sidmeyer.l2shop.core.model.Product;
import sidmeyer.l2shop.core.service.IProductsService;
import sidmeyer.l2shop.dto.CategoryDto;
import sidmeyer.l2shop.dto.ProductDto;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(Api.ADMIN)
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
public class ProductsAdminController {

    private static final Logger LOG = LoggerFactory.getLogger(ProductsAdminController.class);

    @Autowired
    IProductsService productsService;

    @RequestMapping(path = "/test", method = RequestMethod.POST)
    public String test(@RequestBody Product p) {
        LOG.info("Got: " + p);
        return "OOOKKK";
    }

    @RequestMapping(path = Api.Products.PRODUCTS, method = RequestMethod.POST)
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        final Product createdProduct = productsService.createProduct(ProductDtoHelper.productFromDto(productDto));

        final URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path(Api.ROOT + Api.Products.PRODUCTS_ID)
                .buildAndExpand(createdProduct.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @RequestMapping(path = Api.Products.PRODUCTS_ID, method = RequestMethod.PUT)
    public ResponseEntity<ProductDto> updateProduct(@PathVariable long productId, @RequestBody ProductDto productDto) {
        final Product product = ProductDtoHelper.productFromDto(productDto);
        product.setId(productId);
        try {
            productsService.updateProduct(product);
        } catch (ProductNotFoundException e) {
            throw new NotFoundException("Product with ID {} does not exist.", productId);
        }
        productsService.updateProduct(product);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = Api.Products.PRODUCTS_ID, method = RequestMethod.DELETE)
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable long productId) {
        productsService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    //TODO

    @RequestMapping(path = Api.Products.PRODUCTS_CATEGORIES, method = RequestMethod.POST)
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

    @RequestMapping(path = Api.Products.PRODUCTS_CATEGORIES_ID, method = RequestMethod.POST)
    public String addCategoryToProduct(@PathVariable long productId, @PathVariable long categoryId) {
        productsService.addCategoryToProduct(productId, categoryId);
        return "Category with ID " + categoryId + " has been added to product with ID " + productId;
    }

    @RequestMapping(path = Api.Products.PRODUCTS_CATEGORIES_ID, method = RequestMethod.DELETE)
    public String deleteCategoryFromProduct(@PathVariable long productId, @PathVariable long categoryId) {
        productsService.deleteCategoryFromProduct(productId, categoryId);
        return "Category with ID " + categoryId + " has been deleted from product with ID " + productId;
    }
}
