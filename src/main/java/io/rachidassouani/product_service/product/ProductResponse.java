package io.rachidassouani.product_service.product;

public record ProductResponse(
        Long id,
        String name,
        String description,
        double price) {
}
