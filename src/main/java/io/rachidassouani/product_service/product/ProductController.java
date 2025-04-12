package io.rachidassouani.product_service.product;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

        private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDTO> findAllProducts() {
        logger.info("Received request to find all products");
        return productService.findAllProducts();
    }

    @GetMapping("{productId}")
    public ProductDTO findProductById(@PathVariable("productId") Long productId) {
        logger.info("Received request to find product with ID: {}", productId);
        return productService.findProductById(productId);
    }

    @PostMapping
    public ResponseEntity<?> registerProduct(@Valid @RequestBody ProductRegistrationRequest registrationRequest) {
        logger.info("Received request to save product {} ", registrationRequest);
        productService.saveProduct(registrationRequest);
        return ResponseEntity.ok("Product registered");
    }

    @DeleteMapping("{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") Long productId) {
        logger.info("Received request to delete product with ID: {}", productId);
        productService.deleteProductById(productId);
        return ResponseEntity.ok().body("Product Deleted");
    }

    @PutMapping("{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable("productId") Long productId,
                               @Valid @RequestBody ProductUpdateRequest productUpdateRequest) {
        logger.info("Received request to update product with ID: {}", productId);
        productService.updateProduct(productId, productUpdateRequest);
        return ResponseEntity.ok().body("Product Updated");
    }

}
