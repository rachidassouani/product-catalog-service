package io.rachidassouani.product_service.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@Tag(name = "Product", description = "Product management APIs")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "Find all products", description = "Returns list of products")
    public List<ProductDTO> findAllProducts() {
        logger.info("Received request to find all products");
        return productService.findAllProducts();
    }

    @GetMapping("{productId}")
    @Operation(summary = "Find product by ID", description = "Returns a product based on ID")
    public ProductDTO findProductById(@PathVariable("productId") Long productId) {
        logger.info("Received request to find product with ID: {}", productId);
        return productService.findProductById(productId);
    }

    @PostMapping
    @Operation(summary = "Save product", description = "save product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Product already exists")
    })
    public ResponseEntity<?> registerProduct(@Valid @RequestBody ProductRegistrationRequest registrationRequest) {
        logger.info("Received request to save product {} ", registrationRequest);
        productService.saveProduct(registrationRequest);
        return ResponseEntity.ok("Product registered successfully");
    }

    @DeleteMapping("{productId}")
    @Operation(summary = "Delete product by ID", description = "Delete product based on ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product to delete not found")
    })
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") Long productId) {
        logger.info("Received request to delete product with ID: {}", productId);
        productService.deleteProductById(productId);
        return ResponseEntity.ok().body("Product deleted successfully");
    }

    @PutMapping("{productId}")
    @Operation(summary = "Update product by ID", description = "Update product based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<?> updateProduct(@PathVariable("productId") Long productId,
                               @Valid @RequestBody ProductUpdateRequest productUpdateRequest) {
        logger.info("Received request to update product with ID: {}", productId);
        productService.updateProduct(productId, productUpdateRequest);
        return ResponseEntity.ok().body("Product Updated successfully");
    }
}
