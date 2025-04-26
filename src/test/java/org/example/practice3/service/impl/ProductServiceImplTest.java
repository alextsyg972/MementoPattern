package org.example.practice3.service.impl;

import org.example.practice3.entity.Product;
import org.example.practice3.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getAllProducts() {
        List<Product> products = List.of(
                new Product(1L, "first", "desc1", BigDecimal.valueOf(200), 10),
                new Product(2L, "second", "desc2", BigDecimal.valueOf(150), 5)
        );
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();

        assertEquals(2, result.size());
        verify(productRepository, times(1)).findAll();

    }

    @Test
    void getProductById() {
        Product product = new Product(1L, "AA", "d", BigDecimal.valueOf(22), 8);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(1L);

        assertEquals(1L, result.getProductId());
        assertEquals("AA", result.getName());
        verify(productRepository, times(1)).findById(1L);

    }

    @Test
    void createProduct() {
        Product product = new Product(null, "AA", "d", BigDecimal.valueOf(22), 8);
        Product saved = new Product(1L, "AA", "d", BigDecimal.valueOf(312), 8);

        when(productRepository.save(product)).thenReturn(saved);

        Product result = productService.createProduct(product);

        assertEquals(1L, result.getProductId());
        assertEquals("AA", result.getName());
        verify(productRepository).save(product);

    }

    @Test
    void updateProduct() {
        Product existing = new Product(1L, "Old", "Old Desc", BigDecimal.valueOf(50.0), 3);
        Product updated = new Product(null, "New", "New Desc", BigDecimal.valueOf(350.0), 7);

        when(productRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(productRepository.save(any())).thenReturn(existing);

        Product result = productService.updateProduct(1L, updated);

        assertEquals("New", result.getName());
        assertEquals(BigDecimal.valueOf(350.0), result.getPrice());
        verify(productRepository).save(existing);
    }

    @Test
    void deleteProduct() {
        productService.deleteProduct(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }
}