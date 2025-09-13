package dev.carlosezp.ecommerce.service.interfaces;

import dev.carlosezp.ecommerce.payload.Product.ProductDTO;
import dev.carlosezp.ecommerce.payload.Product.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    ProductResponse getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, Long categoryId);
    ProductDTO create(ProductDTO productDTO, Long categoryId);
    void delete(Long productId);
    ProductDTO update(Long productId, ProductDTO productDTO);
    ProductResponse getByCategory(Long categoryId);
    ProductResponse getByKeyWord(String keyword);
    ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;
}
