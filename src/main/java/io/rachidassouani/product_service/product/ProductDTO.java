package io.rachidassouani.product_service.product;

public record ProductDTO(
        Long id,
        String name,
        String description,
        double price) {
}
