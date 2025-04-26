package org.example.practice3.service.impl;

import org.example.practice3.entity.Product;
import org.example.practice3.exception.ResourceNotFoundException;
import org.example.practice3.repository.ProductRepository;
import org.example.practice3.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("product not found"));
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Product updated = getProductById(id);
        updated.setName(product.getName());
        updated.setDescription(product.getDescription());
        updated.setPrice(product.getPrice());
        updated.setQuantityInStock(product.getQuantityInStock());
        return productRepository.save(updated);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
