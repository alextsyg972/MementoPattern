package org.example.practice3.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.practice3.entity.Product;
import org.example.practice3.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllProducts() throws Exception {
        List<Product> products = List.of(
                new Product(1L, "first", "desc1", BigDecimal.valueOf(200), 10),
                new Product(2L, "second", "desc2", BigDecimal.valueOf(150), 5)
        );

        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].productId").value(1))
                .andExpect(jsonPath("$.[0].name").value("first"))
                .andExpect(jsonPath("$.[0].description").value("desc1"))
                .andExpect(jsonPath("$.[1].productId").value(2))
                .andExpect(jsonPath("$.[1].name").value("second"))
                .andExpect(jsonPath("$.[1].price").value(150));
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void getProductById() throws Exception {
        Product product = new Product(1L, "first", "desc1", BigDecimal.valueOf(200), 10);

        when(productService.getProductById(1L)).thenReturn(product);

        mockMvc.perform(get("/api/v1/products/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(1))
                .andExpect(jsonPath("$.name").value("first"))
                .andExpect(jsonPath("$.description").value("desc1"))
                .andExpect(jsonPath("$.quantityInStock").value(10));
        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    void createProduct() throws Exception {
        Product product = new Product(1L, "first", "desc1", BigDecimal.valueOf(200), 10);

        when(productService.createProduct(product)).thenReturn(product);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(1))
                .andExpect(jsonPath("$.name").value("first"))
                .andExpect(jsonPath("$.description").value("desc1"))
                .andExpect(jsonPath("$.quantityInStock").value(10));
        verify(productService, times(1)).createProduct(product);

    }

    @Test
    void updateProduct() throws Exception {
        Product product = new Product(1L, "first", "desc1", BigDecimal.valueOf(200), 10);
        Product updated = new Product(1L, "updatedFirst", "updated", BigDecimal.valueOf(200), 10);

        when(productService.updateProduct(1L,product)).thenReturn(updated);

        mockMvc.perform(put("/api/v1/products/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(1))
                .andExpect(jsonPath("$.name").value("updatedFirst"))
                .andExpect(jsonPath("$.description").value("updated"))
                .andExpect(jsonPath("$.quantityInStock").value(10));
        verify(productService, times(1)).updateProduct(1L, product);


    }

    @Test
    void deleteProduct() throws Exception {
        mockMvc.perform(delete("/api/v1/products/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteProduct(1L);

    }
}