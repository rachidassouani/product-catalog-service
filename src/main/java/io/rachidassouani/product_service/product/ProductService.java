package io.rachidassouani.product_service.product;

import io.rachidassouani.product_service.exception.DuplicateResourceException;
import io.rachidassouani.product_service.exception.RequestValidationException;
import io.rachidassouani.product_service.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductDTOMapper productDTOMapper;

    public ProductService(ProductRepository productRepository, ProductDTOMapper productDTOMapper) {
        this.productRepository = productRepository;
        this.productDTOMapper = productDTOMapper;
    }

    public List<ProductResponse> findAllProducts() {
        return productRepository.findAll().stream()
                .map(productDTOMapper)
                .toList();
    }

    public ProductResponse findProductById(Long productId) {
        return productRepository.findById(productId)
                .map(productDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id [%s] not found".formatted(productId)));

    }


    public void saveProduct(ProductRegistrationRequest productRegistrationRequest) {

        // check if product's name is already exist
        if (productRepository.existsProductByName(productRegistrationRequest.name())) {
            throw new DuplicateResourceException("Product already exists");
        }

        // saving product
        productRepository.save(
                new Product(
                        productRegistrationRequest.name(),
                        productRegistrationRequest.description(),
                        productRegistrationRequest.price()));
    }

    public void deleteProductById(Long productId) {
        // throw exception in case product not exists
        if (!productRepository.existsProductById(productId)) {
            throw new ResourceNotFoundException("Product with id [%s] not found".formatted(productId));
        }
        // delete product by id
        productRepository.deleteById(productId);
    }

    public void updateProduct(Long productId, ProductUpdateRequest productUpdateRequest) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product with id [%s] not found".formatted(productId)));;

        /*
         update the customer only if the request body that accompanies the request contains fields
         that are neither null nor empty and that are not equal to the existing field.
         */
        boolean changes = false;

        if (productUpdateRequest.name() != null && !productUpdateRequest.name().isEmpty()
                && !productUpdateRequest.name().equals(product.getName())) {
            if (productRepository.existsProductByName(productUpdateRequest.name())) {
                throw new DuplicateResourceException("This product is already exists");
            }
            product.setName(productUpdateRequest.name());
            changes = true;
        }

        if (productUpdateRequest.description() != null && !productUpdateRequest.description().isEmpty()
                && !productUpdateRequest.description().equals(product.getDescription())) {

            product.setDescription(productUpdateRequest.description());
            changes = true;
        }

        if (productUpdateRequest.price() != product.getPrice()) {
            product.setPrice(productUpdateRequest.price());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("No data changes found");
        }
        productRepository.save(product);
    }

    public List<ProductResponse> findAllProductsByPageAndSize(int page, int size) {
        Pageable pageable = PageRequest.of(
                page,
                size);
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.getContent()
                .stream()
                .map(productDTOMapper)
                .toList();
    }
}
