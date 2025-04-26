package org.example.practice3.service;

import org.example.practice3.entity.Order;

public interface OrderService {

    Order createNewOrder(Order order);

    Order getOrderById(Long id);

}
