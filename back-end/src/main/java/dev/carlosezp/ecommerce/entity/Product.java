package dev.carlosezp.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;
    @NotNull
    @NotBlank
    @Size(min = 5,max = 128, message = "Product Name must be at least 5 characters and not exceed 128 characters")
    private String name;
    @NotNull
    @NotBlank
    @Size(min = 5,max = 512, message = "Product Description must be at least 5 characters and not exceed 512 characters")
    private String description;
    private String image;
    @NotNull
    private Integer quantity;
    @NotNull
    private double price;
    @NotNull
    private double discount;
    @NotNull
    private double specialPrice;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public void updateSpecialPrice(Double discount){
        this.specialPrice = this.price - (this.price * (discount / 100));
    }
    public void updateSpecialPrice(){
        this.specialPrice = this.price - (this.price * (this.discount / 100));
    }
}
