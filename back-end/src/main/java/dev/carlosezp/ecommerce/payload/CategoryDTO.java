package dev.carlosezp.ecommerce.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long categoryId;
    @NotBlank(message = "Cannot be blank")
    @Size(min = 5, message = "Must contain at least 5 characters")
    private String categoryName;
}
