package io.rachidassouani.product_service.product;

import io.rachidassouani.product_service.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductDTOMapper productDTOMapper;

    @InjectMocks
    private ProductService productService;

    @Test
    public void testFindProductById() {
        // preparing product object
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setName("title");
        product.setDescription("description");
        product.setPrice(300);

        ProductResponse expectedProductResponse = new ProductResponse(
                productId, "title", "description", 300);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(productDTOMapper.apply(product)).thenReturn(expectedProductResponse);

        // testing
        ProductResponse returnedProductResponse = productService.findProductById(productId);

        // asserts
        assertThat(returnedProductResponse.id()).isEqualTo(expectedProductResponse.id());
        assertThat(returnedProductResponse.name()).isEqualTo(expectedProductResponse.name());
        assertThat(returnedProductResponse.description()).isEqualTo(expectedProductResponse.description());
        assertThat(returnedProductResponse.price()).isEqualTo(expectedProductResponse.price());

        verify(productRepository).findById(productId);
        verify(productDTOMapper).apply(product);
    }

    @Test
    public void testSaveProduct() {
        // product to save
        var productToSave = new ProductRegistrationRequest(
                "Test Product",
                "Test Description",
                50.99);

        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setName("Test Product");
        savedProduct.setDescription("Test Description");
        savedProduct.setPrice(50.99);

        ProductResponse expectedProductResponse = new ProductResponse
                (1L, "Test Product", "Test Description", 50.99);

        when(productRepository.save(any())).thenReturn(savedProduct);
        when(productDTOMapper.apply(any())).thenReturn(expectedProductResponse);

        ProductResponse savedProductResponse = productService.saveProduct(productToSave);

        // Assert
        assertThat(savedProductResponse.id()).isEqualTo(expectedProductResponse.id());
        assertThat(savedProductResponse.name()).isEqualTo(expectedProductResponse.name());
        assertThat(savedProductResponse.description()).isEqualTo(expectedProductResponse.description());
        assertThat(savedProductResponse.price()).isEqualTo(expectedProductResponse.price());

        verify(productRepository).save(any(Product.class));
    }

    @Test
    public void testDeleteProduct() {
        Long id = 1L;

        when(productRepository.existsProductById(anyLong())).thenReturn(true);

        productService.deleteProductById(id);

        verify(productRepository).deleteById(id);
        verify(productRepository).existsProductById(id);
    }

    @Test
    public void testDeleteProductWhenProductDoesNotExist() {
        Long id = 1L;

        when(productRepository.existsProductById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            productService.deleteProductById(id);
        });

        verify(productRepository, never()).deleteById(anyLong());
        verify(productRepository).existsProductById(id);
    }

}
