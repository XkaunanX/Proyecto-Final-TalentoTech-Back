package com.ecommerce.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class OrderResponseDTO {
    private Long orderId;
    private LocalDateTime orderDate;
    private Double totalCost;
    private List<OrderDetailResponseDTO> orderDetails;

}
