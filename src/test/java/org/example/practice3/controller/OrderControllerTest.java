package org.example.practice3.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.practice3.entity.Customer;
import org.example.practice3.entity.Order;
import org.example.practice3.entity.Product;
import org.example.practice3.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createNewOrder() throws Exception {
        Customer customer = new Customer(1L, "Alex", "Tsyg", "tsyg@example.com", "123456789");
        Product product1 = new Product(1L, "Laptop", "Desc", BigDecimal.valueOf(1000), 10);
        Product product2 = new Product(2L, "Mouse", "Desc", BigDecimal.valueOf(50), 100);

        Order inputOrder = new Order();
        inputOrder.setCustomer(customer);
        inputOrder.setProducts(List.of(product1, product2));
        inputOrder.setShippingAddress("Test Address");

        when(orderService.createNewOrder(inputOrder)).thenReturn(inputOrder);

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputOrder)))
                .andExpect(status().isCreated());
        verify(orderService,times(1)).createNewOrder(inputOrder);
    }

    @Test
    void getOrderById() throws Exception {
        Customer customer = new Customer(1L, "Alex", "Tsyg", "tsyg@example.com", "123456789");
        Product product1 = new Product(1L, "Laptop", "Desc", BigDecimal.valueOf(1000), 10);
        Product product2 = new Product(2L, "Mouse", "Desc", BigDecimal.valueOf(50), 100);

        Order inputOrder = new Order();
        inputOrder.setOrderId(1L);
        inputOrder.setCustomer(customer);
        inputOrder.setProducts(List.of(product1, product2));
        inputOrder.setShippingAddress("Test Address");

        when(orderService.getOrderById(1L)).thenReturn(inputOrder);

        mockMvc.perform(get("/api/v1/orders/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.orderId").value(1));
        verify(orderService, times(1)).getOrderById(1L);

    }
}