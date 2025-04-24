package org.example.practice1.Entity;


import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import org.example.practice1.config.View;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "\"order\"")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(View.UserDetails.class)
    private long id;

    @JsonView(View.UserDetails.class)
    private BigDecimal sum;

    @JsonView(View.UserDetails.class)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;


    public Order() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && Objects.equals(sum, order.sum) && orderStatus == order.orderStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sum, orderStatus);
    }
}
