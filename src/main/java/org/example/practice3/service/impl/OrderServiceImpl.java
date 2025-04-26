package org.example.practice3.service.impl;

import org.example.practice3.entity.Customer;
import org.example.practice3.entity.Order;
import org.example.practice3.entity.OrderStatus;
import org.example.practice3.entity.Product;
import org.example.practice3.exception.ResourceNotFoundException;
import org.example.practice3.repository.CustomerRepository;
import org.example.practice3.repository.OrderRepository;
import org.example.practice3.repository.ProductRepository;
import org.example.practice3.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Order createNewOrder(Order order) {
        Customer customer = customerRepository.findById(order.getCustomer().getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        List<Product> products = order.getProducts().stream()
                .map(p -> productRepository.findById(p.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found")))
                .toList();

        BigDecimal total = BigDecimal.valueOf(products.stream().mapToDouble(x -> x.getPrice().doubleValue()).sum());
        order.setCustomer(customer);
        order.setProducts(products);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.ACCEPTED);
        order.setTotalPrice(total);

        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order with this id: " + id + " not found"));
    }
}
