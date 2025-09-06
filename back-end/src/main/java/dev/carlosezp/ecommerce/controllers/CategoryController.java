package dev.carlosezp.ecommerce.controllers;

import dev.carlosezp.ecommerce.interfaces.CategoryService;
import dev.carlosezp.ecommerce.models.Category;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
public class CategoryController {
    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/api/public/categories")
    public ResponseEntity<List<Category>> getAll(){
        List<Category> categories = categoryService.getAll();
        return new ResponseEntity<>(categories,HttpStatus.OK);
    }

    @PostMapping("/api/public/categories")
    public ResponseEntity<String> add(@Valid @RequestBody Category category){
        String status = categoryService.add(category);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @DeleteMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<String> delete(@PathVariable Long categoryId){
        String status = categoryService.delete(categoryId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PutMapping("/api/public/categories")
    public ResponseEntity<String> update(@Valid @RequestBody Category category){
        String status = categoryService.update(category);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
