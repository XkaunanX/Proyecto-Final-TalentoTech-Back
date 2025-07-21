package com.ecommerce.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "order_detail")
public class OrderDetail {

    @EmbeddedId
    private OrderDetailId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")  // Mapea orderId dentro de la clave compuesta
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId") // Mapea productId dentro de la clave compuesta
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double unitPrice;

    public OrderDetail() {}

    public OrderDetail(Order order, Product product, Integer quantity, Double unitPrice) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.id = new OrderDetailId(order.getId(), product.getId());
    }
}
