package com.ecommerce.api.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDTO {

    @NotBlank(message = "Name is mandatory")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String name;

    @NotNull(message = "Price is mandatory")
    @DecimalMin(value = "0.01", inclusive = true, message = "Price must be greater than 0")
    private Double price;

    @NotNull(message = "Stock is mandatory")
    @Min(value = 0, message = "Stock cannot be negative")
    private Long stock;

    @Size(max = 255, message = "Description can be up to 255 characters")
    private String description;

    public ProductRequestDTO() {}

    public ProductRequestDTO(String name, Double price, Long stock, String description) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
    }
}
