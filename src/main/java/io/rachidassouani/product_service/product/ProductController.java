package io.rachidassouani.product_service.product;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDTO> findAllProducts() {
        return productService.findAllProducts();
    }

    @GetMapping("{productId}")
    public ProductDTO findProductById(@PathVariable("productId") Long productId) {
        return productService.findProductById(productId);
    }

    @PostMapping
    public ResponseEntity<?> registerProduct(@Valid @RequestBody ProductRegistrationRequest registrationRequest) {
        productService.saveProduct(registrationRequest);
        return ResponseEntity.ok("Product registered");
    }

    @DeleteMapping("{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") Long productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.ok().body("Product Deleted");
    }

    @PutMapping("{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable("productId") Long productId,
                               @Valid @RequestBody ProductUpdateRequest productUpdateRequest) {
        productService.updateProduct(productId, productUpdateRequest);
        return ResponseEntity.ok().body("Product Updated");
    }

}
