package io.rachidassouani.product_service.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsProductByName(String name);
    boolean existsProductById(Long id);
}
