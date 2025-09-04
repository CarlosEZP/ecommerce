package dev.carlosezp.ecommerce.interfaces;

import dev.carlosezp.ecommerce.models.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();
    String add(Category category);
    String delete(Long categoryId);
    String update(Category category);
}
