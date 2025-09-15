package dev.carlosezp.ecommerce.repository;

import dev.carlosezp.ecommerce.entity.Category;
import dev.carlosezp.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);
    Page<Product> findByCategory(Category category, Pageable pageable);
    List<Product> findByCategory(Category category);
    Collection<Object> findByNameContainingIgnoreCase(String keyword);
    Product findByNameAndCategoryId(String name, Long categoryId);
}
