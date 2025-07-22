package com.ecommerce.api.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "purchase_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;
    private double total;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public Order() {
        this.date = LocalDateTime.now();
    }

    public Order(double total) {
        this.date = LocalDateTime.now();
        this.total = total;
    }

    public void addOrderDetail(OrderDetail detail) {
        orderDetails.add(detail);
        detail.setOrder(this); // relacion bidireccional
    }

    public void calculateTotal() {
        this.total = orderDetails.stream()
                .mapToDouble(od -> od.getUnitPrice() * od.getQuantity())
                .sum();
    }
}
