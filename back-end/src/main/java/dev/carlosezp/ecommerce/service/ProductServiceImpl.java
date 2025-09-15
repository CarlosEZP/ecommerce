package dev.carlosezp.ecommerce.service;

import dev.carlosezp.ecommerce.entity.Category;
import dev.carlosezp.ecommerce.entity.Product;
import dev.carlosezp.ecommerce.exception.APIException;
import dev.carlosezp.ecommerce.exception.ResourceNotFoundException;
import dev.carlosezp.ecommerce.payload.Product.ProductDTO;
import dev.carlosezp.ecommerce.payload.Product.ProductResponse;
import dev.carlosezp.ecommerce.repository.CategoryRepository;
import dev.carlosezp.ecommerce.repository.ProductRepository;
import dev.carlosezp.ecommerce.service.interfaces.FileService;
import dev.carlosezp.ecommerce.service.interfaces.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

import static dev.carlosezp.ecommerce.config.AppConstants.IMAGES_PATH;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    FileService fileService;

    @Override
    public ProductResponse getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, Long categoryId) {
        if(!sortOrder.equals("asc") && !sortOrder.equals("desc"))
            throw new APIException(String.format("Sort Order \"%s\" is not valid",sortOrder));

        sortBy = sortByMap(sortBy);
        Sort sort = sortOrder.equalsIgnoreCase("asc")?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Category category = null;
        if(categoryId != 0)
            category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category","id",categoryId));

        Pageable details = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> pageProduct = category != null ? productRepository.findByCategory(category,details) : productRepository.findAll(details);

        if(pageProduct.isEmpty())
            throw new ResourceNotFoundException("No product created yet");

        List<Product> productList = pageProduct.getContent();
        List<ProductDTO> productDTOList= productList
                .stream()
                .map(x -> modelMapper.map(x,ProductDTO.class))
                .toList();

        ProductResponse response = new ProductResponse();
        response.setContent(productDTOList);
        response.setPageNumber(pageProduct.getNumber());
        response.setPageSize(pageProduct.getSize());
        response.setTotalPages((long)pageProduct.getTotalPages());
        response.setTotalElements(pageProduct.getTotalElements());
        response.setLastPage(pageProduct.isLast());

        return response;
    }

    @Override
    public ProductDTO create(ProductDTO productDTO, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","id",categoryId));

        if(productRepository.findByNameAndCategoryId(productDTO.getProductName(),category.getId()) != null)
            throw new APIException(String.format("Product with the name \"%s\" already exists in category \"%s\"",productDTO.getProductName(),category.getName()));

        Product newProduct = modelMapper.map(productDTO,Product.class);
        newProduct.setCategory(category);
        newProduct.updateSpecialPrice();

        return modelMapper.map(productRepository.save(newProduct),ProductDTO.class);
    }

    @Override
    public void delete(Long productId) {
        Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new ResourceNotFoundException("Product","id",productId));
        productRepository.delete(product);
    }

    @Override
    public ProductDTO update(Long productId, ProductDTO productDTO) {
        productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product","id",productId));
        Product updatedProduct = modelMapper.map(productDTO,Product.class);
        updatedProduct.setId(productId);
        updatedProduct.updateSpecialPrice(updatedProduct.getDiscount());
        return modelMapper.map(productRepository.save(updatedProduct),ProductDTO.class);
    }

    @Override
    public ProductResponse getByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","id",categoryId));

        List<ProductDTO> productDTOList = productRepository.findByCategory(category).stream()
                .map(product -> modelMapper.map(product,ProductDTO.class))
                .toList();

        if(productDTOList.isEmpty())
            throw new ResourceNotFoundException("No product created yet");

        ProductResponse response = new ProductResponse();
        response.setContent(productDTOList);

        return response;
    }

    @Override
    public ProductResponse getByKeyWord(String keyword) {
        List<ProductDTO> productDTOList = productRepository.findByNameContainingIgnoreCase(keyword).stream()
                .map(product -> modelMapper.map(product,ProductDTO.class))
                .toList();
        if(productDTOList.isEmpty())
            throw new ResourceNotFoundException("No product created yet");
        ProductResponse response = new ProductResponse();
        response.setContent(productDTOList);
        return response;
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product","id",productId));
        String fileName = fileService.uploadImage(IMAGES_PATH, image);
        product.setImage(fileName);
        return modelMapper.map(productRepository.save(product), ProductDTO.class);
    }

    private static String sortByMap(String sortBy){
        if(sortBy.equals("productName"))
            return "name";
        if(sortBy.equals("productId"))
            return "id";
        return sortBy;
    }
}
