package dev.carlosezp.ecommerce.services;

import dev.carlosezp.ecommerce.exceptions.APIException;
import dev.carlosezp.ecommerce.exceptions.ResourceNotFoundException;
import dev.carlosezp.ecommerce.interfaces.CategoryService;
import dev.carlosezp.ecommerce.models.Category;
import dev.carlosezp.ecommerce.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public List<Category> getAll() {
        List<Category> categories= categoryRepository.findAll();
        if(categories.isEmpty())
            throw new ResourceNotFoundException("No category registered yet");
        return categories;
    }
    @Override
    public String add(Category category) {
        if(categoryRepository.findByName(category.getName()) != null)
            throw new APIException(String.format("Category with the name \"%s\" already exists",category.getName()));
        categoryRepository.save(category);
        return "Add new " + category.getName() + " category successfully";
    }
    @Override
    public String delete(Long categoryId){
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","id",categoryId));
        categoryRepository.delete(category);
        return "Delete successfully";
    }
    @Override
    public String update(Category category) {
        categoryRepository.findById(category.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category","id",category.getId()));
        categoryRepository.save(category);
        return "Update category successfully: " + category;
    }

}
