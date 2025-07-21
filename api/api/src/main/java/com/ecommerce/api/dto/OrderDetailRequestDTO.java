package com.ecommerce.api.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderDetailRequestDTO {
    private Long productId;
    private int quantity;
}
