package dev.carlosezp.ecommerce.controller;

import dev.carlosezp.ecommerce.service.interfaces.CategoryService;
import dev.carlosezp.ecommerce.payload.Category.CategoryDTO;
import dev.carlosezp.ecommerce.payload.Category.CategoryResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static dev.carlosezp.ecommerce.config.AppConstants.*;

@RestController()
public class CategoryController {
    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/api/public/categories")
    public ResponseEntity<CategoryResponse> getAll(
            @RequestParam(name = "pageNumber", defaultValue = PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = SORT_CATEGORIES_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = SORT_ORDER, required = false) String sortOrder){
        CategoryResponse categoryResponse = categoryService.getAll(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(categoryResponse,HttpStatus.OK);
    }

    @PostMapping("/api/public/categories")
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO response = categoryService.create(categoryDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/api/admin/categories/{categoryId}")
    public void delete(@PathVariable Long categoryId){
        categoryService.delete(categoryId);
    }

    @PutMapping("/api/public/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> update(@Valid @PathVariable Long categoryId,
                                         @RequestBody CategoryDTO categoryDTOUpdated){
        CategoryDTO response = categoryService.update(categoryId, categoryDTOUpdated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
