package dev.carlosezp.ecommerce.entity;

import jakarta.persistence.*;
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
    private String name;
    private String description;
    private String image;
    private Integer quantity;
    private double price;
    private double discount;
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
