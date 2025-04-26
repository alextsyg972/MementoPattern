package org.example.practice3.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "\"order\"")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToMany
    private List<Product> products;

    private LocalDateTime orderDate;

    @NotBlank(message = "Shipping address can't be blank")
    private String shippingAddress;

    @PositiveOrZero
    private BigDecimal totalPrice;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId) && Objects.equals(customer, order.customer) && Objects.equals(products, order.products) && Objects.equals(orderDate, order.orderDate) && Objects.equals(shippingAddress, order.shippingAddress) && Objects.equals(totalPrice, order.totalPrice) && orderStatus == order.orderStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, customer, products, orderDate, shippingAddress, totalPrice, orderStatus);
    }
}
