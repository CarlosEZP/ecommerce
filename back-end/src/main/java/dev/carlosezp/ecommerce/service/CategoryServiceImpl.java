package dev.carlosezp.ecommerce.service;

import dev.carlosezp.ecommerce.exception.APIException;
import dev.carlosezp.ecommerce.exception.ResourceNotFoundException;
import dev.carlosezp.ecommerce.service.interfaces.CategoryService;
import dev.carlosezp.ecommerce.entity.Category;
import dev.carlosezp.ecommerce.payload.Category.CategoryDTO;
import dev.carlosezp.ecommerce.payload.Category.CategoryResponse;
import dev.carlosezp.ecommerce.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        sortBy = sortByMap(sortBy);
        Sort sort = sortOrder.equalsIgnoreCase("asc") ?
                        Sort.by(sortBy).ascending() :
                        Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sort);
        Page<Category> pageCategory = categoryRepository.findAll(pageDetails);
        List<Category> categories = pageCategory.getContent();

        if(categories.isEmpty())
            throw new ResourceNotFoundException("No category registered yet");

        List<CategoryDTO> dtoList = categories.stream()
                .map(category -> modelMapper.map(category,CategoryDTO.class))
                .toList();

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(dtoList);
        categoryResponse.setPageNumber(pageCategory.getNumber());
        categoryResponse.setPageSize(pageCategory.getSize());
        categoryResponse.setTotalElements(pageCategory.getTotalElements());
        categoryResponse.setTotalPages((long) pageCategory.getTotalPages());
        categoryResponse.setLastPage(pageCategory.isLast());
        return categoryResponse;
    }
    @Override
    public CategoryDTO create(CategoryDTO newCategory) {
        if(newCategory.getCategoryName() == null)
            throw new APIException("Invalid format");
        if(categoryRepository.findByName(newCategory.getCategoryName()) != null)
            throw new APIException(String.format("Category with the name \"%s\" already exists",newCategory.getCategoryName()));
        Category category = modelMapper.map(newCategory,Category.class);
        return modelMapper.map(categoryRepository.save(category),CategoryDTO.class);
    }
    @Override
    public void delete(Long categoryId){
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","id",categoryId));
        categoryRepository.delete(category);
    }
    @Override
    public CategoryDTO update(Long categoryId, CategoryDTO newCategory) {
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","id",categoryId));
        newCategory.setCategoryId(categoryId);
        Category category = modelMapper.map(newCategory,Category.class);
        return modelMapper.map(categoryRepository.save(category),CategoryDTO.class);
    }

    private static String sortByMap(String sortBy){
        if(sortBy.equals("categoryName"))
            return "name";
        if(sortBy.equals("categoryId"))
            return "id";
        return null;
    }

}
