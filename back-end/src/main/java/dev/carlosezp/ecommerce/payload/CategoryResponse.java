package dev.carlosezp.ecommerce.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Long totalPages;
    private boolean lastPage;
    private List<CategoryDTO> content;
}
