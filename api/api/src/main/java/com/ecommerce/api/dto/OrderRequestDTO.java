package com.ecommerce.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OrderRequestDTO {
    private List<OrderDetailRequestDTO> orderDetails;

}
