package dev.carlosezp.ecommerce.payload.Product;

import dev.carlosezp.ecommerce.payload.Category.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long productId;
    private String productName;
    private String productDescription;
    private String image;
    private Integer quantity;
    private double price;
    private double discount;
    private double specialPrice;
}
