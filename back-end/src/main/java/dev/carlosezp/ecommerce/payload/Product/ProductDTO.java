package dev.carlosezp.ecommerce.payload.Product;

import dev.carlosezp.ecommerce.payload.Category.CategoryDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long productId;
    @NotBlank
    @Size(min = 5,max = 128, message = "Product Name must be at least 5 characters and not exceed 128 characters")
    private String productName;
    @NotBlank
    @Size(min = 5,max = 512, message = "Product Description must be at least 5 characters and not exceed 512 characters")
    private String productDescription;
    private String image;
    @NotBlank
    @Size(min = 1, message = "Product quantity must be at least 1")
    private Integer quantity;
    @NotBlank
    private double price;
    @NotBlank
    private double discount;
    private double specialPrice;
}
