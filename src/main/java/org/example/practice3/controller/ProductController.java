package org.example.practice3.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.practice3.entity.Product;
import org.example.practice3.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;
    private final ObjectMapper objectMapper;

    @Autowired
    public ProductController(ProductService productService, ObjectMapper objectMapper) {
        this.productService = productService;
        this.objectMapper = objectMapper;
    }

    @GetMapping(value = "/products", produces = "application/json")
    public ResponseEntity<String> getAllProducts() throws JsonProcessingException {
        String jsonAnswer = objectMapper.writeValueAsString(productService.getAllProducts());
        return new ResponseEntity<>(jsonAnswer, HttpStatus.OK);
    }

    @GetMapping(value = "/products/{id}", produces = "application/json")
    public ResponseEntity<String> getProductById(@PathVariable String id) throws JsonProcessingException {
        Long productId = objectMapper.readValue(id, Long.class);
        String jsonAnswer = objectMapper.writeValueAsString(productService.getProductById(productId));
        return new ResponseEntity<>(jsonAnswer, HttpStatus.OK);
    }

    @PostMapping(value = "/products", produces = "application/json")
    public ResponseEntity<String> createProduct(@RequestBody String jsonProduct) throws JsonProcessingException {
        Product product = objectMapper.readValue(jsonProduct, Product.class);
        String jsonAnswer = objectMapper.writeValueAsString(productService.createProduct(product));
        return new ResponseEntity<>(jsonAnswer, HttpStatus.CREATED);
    }

    @PutMapping(value = "/products/{id}", produces = "application/json")
    public ResponseEntity<String> updateProduct(@PathVariable String id, @RequestBody String jsonProduct) throws JsonProcessingException {
        Long productId = objectMapper.readValue(id, Long.class);
        Product product = objectMapper.readValue(jsonProduct, Product.class);
        String jsonAnswer = objectMapper.writeValueAsString(productService.updateProduct(productId, product));
        return new ResponseEntity<>(jsonAnswer, HttpStatus.OK);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable String id) throws JsonProcessingException {
        Long productId = objectMapper.readValue(id, Long.class);
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
