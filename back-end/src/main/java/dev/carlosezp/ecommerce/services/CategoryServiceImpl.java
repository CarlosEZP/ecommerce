package dev.carlosezp.ecommerce.services;

import dev.carlosezp.ecommerce.interfaces.CategoryService;
import dev.carlosezp.ecommerce.models.Category;
import dev.carlosezp.ecommerce.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
    @Override
    public String add(Category category) {
        categoryRepository.save(category);
        return "Add new " + category.getName() + " category successfully";
    }
    @Override
    public String delete(Long categoryId){
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not Found!"));
        categoryRepository.delete(category);
        return "Delete successfully";
    }
    @Override
    public String update(Category category) {
        categoryRepository.findById(category.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not Found!"));
        categoryRepository.save(category);
        return "Update category successfully: " + category;
    }

}
