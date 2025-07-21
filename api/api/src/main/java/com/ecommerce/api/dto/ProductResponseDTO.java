package com.ecommerce.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDTO {

    private Long id;
    private String name;
    private Double price;
    private Long stock;
    private String description;

    public ProductResponseDTO() {}

    public ProductResponseDTO(Long id, String name, Double price, Long stock, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
    }
}
