package com.ecommerce.api.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private Double price;
    private Long stock;
    private String description;

    public Product(){}

    public Product(long id, String name, Double price, Long stock, String description){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
    }
}
