package sidmeyer.l2shop.core.controller.dtohelpers;

import sidmeyer.l2shop.core.model.Category;
import sidmeyer.l2shop.core.model.Product;
import sidmeyer.l2shop.dto.CategoryDto;
import sidmeyer.l2shop.dto.ProductDto;

import java.util.List;
import java.util.stream.Collectors;

public class ProductDtoHelper {

    public static ProductDto productToDto(final Product product) {
        ProductDto dto = new ProductDto();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setImageUrl(product.getImageUrl());
        dto.setInStock(product.getInStock());

        List<CategoryDto> categories = product.getCategories().stream()
                .map(CategoryDtoHelper::categoryToDto)
                .collect(Collectors.toList());
        dto.setCategories(categories);

        return dto;
    }

    public static Product productFromDto(final ProductDto dto) {
        Product product = new Product();

        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setImageUrl(dto.getImageUrl());
        product.setInStock(dto.getInStock());

        if (dto.getCategories() != null) {
            List<Category> categories = dto.getCategories().stream()
                    .map(CategoryDtoHelper::categoryFromDto)
                    .collect(Collectors.toList());

            product.setCategories(categories);
        }

        return product;
    }
}
