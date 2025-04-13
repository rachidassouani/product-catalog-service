package io.rachidassouani.product_service.product;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProductDTOMapper implements Function<Product, ProductResponse> {

    @Override
    public ProductResponse apply(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice());
    }
}
