package org.example.practice3.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.practice3.entity.Order;
import org.example.practice3.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderService orderService;
    private final ObjectMapper objectMapper;

    @Autowired
    public OrderController(OrderService orderService, ObjectMapper objectMapper) {
        this.orderService = orderService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(value = "/orders", produces = "application/json")
    public ResponseEntity<String> createNewOrder(@RequestBody String json) throws JsonProcessingException {
        Order order = (objectMapper.readValue(json, Order.class));
        String jsonAnswer = objectMapper.writeValueAsString(orderService.createNewOrder(order));
        return new ResponseEntity<>(jsonAnswer, HttpStatus.CREATED);
    }

    @GetMapping(value = "/orders/{id}", produces = "application/json")
    public ResponseEntity<String> getOrderById(@PathVariable String id) throws JsonProcessingException {
        Long orderId = objectMapper.readValue(id, Long.class);
        String jsonAnswer = objectMapper.writeValueAsString(orderService.getOrderById(orderId));
        return new ResponseEntity<>(jsonAnswer, HttpStatus.OK);
    }

}
