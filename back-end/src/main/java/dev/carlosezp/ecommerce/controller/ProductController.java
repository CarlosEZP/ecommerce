package dev.carlosezp.ecommerce.controller;

import dev.carlosezp.ecommerce.payload.Product.ProductResponse;
import dev.carlosezp.ecommerce.service.interfaces.ProductService;
import dev.carlosezp.ecommerce.payload.Product.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static dev.carlosezp.ecommerce.config.AppConstants.*;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAll(
            @RequestParam(defaultValue = PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(defaultValue = SORT_PRODUCT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = SORT_ORDER, required = false) String sortOrder,
            @RequestParam(defaultValue = "0", required = false) Long categoryId
            ){
        ProductResponse response = productService.getAll(pageNumber,pageSize,sortBy,sortOrder,categoryId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable(name = "categoryId") Long categoryId){
        return new ResponseEntity<>(productService.getByCategory(categoryId),HttpStatus.OK);
    }

    @GetMapping("/public/products/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable String keyword){
        return new ResponseEntity<>(productService.getByKeyWord(keyword),HttpStatus.OK);
    }

    @PostMapping("/admin/categories/{categoryId}/products")
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO productDTO,
                                                 @PathVariable("categoryId") Long categoryId){
        return new ResponseEntity<>(productService.create(productDTO,categoryId), HttpStatus.CREATED);
    }
    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long productId,
                                             @RequestBody ProductDTO productDTO){
        return new ResponseEntity<>(productService.update(productId,productDTO),HttpStatus.OK);
    }
    @DeleteMapping("/admin/products/{productId}")
    public void delete(@PathVariable Long productId){
        productService.delete(productId);
    }
    @PutMapping("/admin/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable("productId") Long productId,
                                                         @RequestParam("Image") MultipartFile image) throws IOException {
        return new ResponseEntity<>(productService.updateProductImage(productId,image), HttpStatus.OK);
    }
}
