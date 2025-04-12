package io.rachidassouani.product_service.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProductRegistrationRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name must be at most 100 characters")
        String name,
        String description,

        @DecimalMin(value = "0.0", inclusive = true, message = "Price must be non-negative")
        double price) {
}
