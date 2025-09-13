package dev.carlosezp.ecommerce.service.interfaces;

import dev.carlosezp.ecommerce.payload.Category.CategoryDTO;
import dev.carlosezp.ecommerce.payload.Category.CategoryResponse;

public interface CategoryService {
    CategoryResponse getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    CategoryDTO create(CategoryDTO category);
    void delete(Long categoryId);
    CategoryDTO update(Long categoryId, CategoryDTO category);
}
