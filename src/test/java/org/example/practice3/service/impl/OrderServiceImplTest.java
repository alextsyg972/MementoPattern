package org.example.practice3.service.impl;

import org.example.practice3.entity.Customer;
import org.example.practice3.entity.Order;
import org.example.practice3.entity.Product;
import org.example.practice3.repository.CustomerRepository;
import org.example.practice3.repository.OrderRepository;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createNewOrder() {
        Customer customer = new Customer(1L, "Alex", "Tsyg", "tsyg@example.com", "123456789");
        Product product1 = new Product(1L, "Laptop", "Desc", BigDecimal.valueOf(1000), 10);
        Product product2 = new Product(2L, "Mouse", "Desc", BigDecimal.valueOf(50), 100);

        Order inputOrder = new Order();
        inputOrder.setCustomer(customer);
        inputOrder.setProducts(List.of(product1, product2));
        inputOrder.setShippingAddress("Test Address");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(productRepository.findById(2L)).thenReturn(Optional.of(product2));
        when(orderRepository.save(inputOrder)).thenReturn(inputOrder);

        Order result = orderService.createNewOrder(inputOrder);

        assertEquals(BigDecimal.valueOf(1050.0), result.getTotalPrice());
        assertEquals("ACCEPTED", result.getOrderStatus().name());
        assertNotNull(result.getOrderDate());
        verify(orderRepository, times(1)).save(inputOrder);

    }

    @Test
    void getOrderById() {
        Customer customer = new Customer(1L, "Alex", "Tsyg", "tsyg@example.com", "123456789");
        Product product1 = new Product(1L, "Laptop", "Desc", BigDecimal.valueOf(1000), 10);
        Product product2 = new Product(2L, "Mouse", "Desc", BigDecimal.valueOf(50), 100);

        Order order = new Order();
        order.setCustomer(customer);
        order.setProducts(List.of(product1, product2));
        order.setShippingAddress("Test Address");

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        Order result = orderService.getOrderById(1L);

        assertEquals(result, order);
        verify(orderRepository, times(1)).findById(1L);

    }
}