package sidmeyer.l2shop.core.controller.dtohelpers;

import sidmeyer.l2shop.core.model.Category;
import sidmeyer.l2shop.dto.CategoryDto;

public class CategoryDtoHelper {

    public static CategoryDto categoryToDto(final Category category) {
        CategoryDto dto = new CategoryDto();

        dto.setId(category.getId());
        dto.setName(category.getName());
        if (category.getParentCategory() != null) {
            dto.setParentId(category.getParentCategory().getId());
        }

        return dto;
    }

    public static Category categoryFromDto(final CategoryDto dto) {
        Category category = new Category();

        category.setId(dto.getId());
        category.setName(dto.getName());

        if (dto.getParentId() > 0) {
            Category parentCategory = new Category();
            parentCategory.setId(dto.getParentId());
            category.setParentCategory(parentCategory);
        }

        return category;
    }
}
