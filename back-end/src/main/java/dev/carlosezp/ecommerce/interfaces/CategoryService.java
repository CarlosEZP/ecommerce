package dev.carlosezp.ecommerce.interfaces;

import dev.carlosezp.ecommerce.payload.CategoryDTO;
import dev.carlosezp.ecommerce.payload.CategoryResponse;

public interface CategoryService {
    CategoryResponse getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    CategoryDTO create(CategoryDTO category);
    void delete(Long categoryId);
    CategoryDTO update(Long categoryId, CategoryDTO category);
}
