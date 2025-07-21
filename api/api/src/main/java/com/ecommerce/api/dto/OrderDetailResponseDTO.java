package com.ecommerce.api.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderDetailResponseDTO {
    private Long productId;
    private String productName;
    private int quantity;
    private Double unitPrice;

}
