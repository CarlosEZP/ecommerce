package dev.carlosezp.ecommerce.services;

import dev.carlosezp.ecommerce.interfaces.CategoryService;
import dev.carlosezp.ecommerce.models.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    List<Category> categories = new ArrayList<Category>();
    Long nextId = 0L;
    @Override
    public List<Category> getAll() {
        return categories;
    }
    @Override
    public String add(Category category) {
        category.setId(++nextId);
        categories.add(category);
        return "Add new " + category.getName() + " category successfully";
    }
    @Override
    public String delete(Long categoryId){
        Category category = categories.stream()
                .filter(x -> x.getId().equals(categoryId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not Found!"));
        categories.remove(category);
        return "Delete successfully";
    }
    @Override
    public String update(Category category) {
        Category categoryUpdated = categories.stream()
                .filter(x -> x.getId().equals(category.getId()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not Found!"));
        categoryUpdated.setName(category.getName());
        return "Update category successfully: " + categoryUpdated;
    }

}
