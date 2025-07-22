package com.ecommerce.api.service;

import com.ecommerce.api.dto.OrderDetailRequestDTO;
import com.ecommerce.api.dto.OrderRequestDTO;
import com.ecommerce.api.entity.Order;
import com.ecommerce.api.entity.OrderDetail;
import com.ecommerce.api.entity.Product;
import com.ecommerce.api.exception.InsufficientStockException;
import com.ecommerce.api.exception.OrderNotFoundException;
import com.ecommerce.api.repository.OrderRepository;
import com.ecommerce.api.repository.ProductRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Order createOrder(@Valid OrderRequestDTO orderRequest) {
        Order order = new Order();
        double total = 0.0;

        for (OrderDetailRequestDTO detailDTO : orderRequest.getOrderDetails()) {
            Long productId = detailDTO.getProductId();
            int quantity = detailDTO.getQuantity();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));

            if (product.getStock() < quantity) {
                throw new InsufficientStockException("Not enough stock for product: " + product.getName());
            }

            product.setStock(product.getStock() - quantity);
            productRepository.save(product);

            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(product);
            detail.setQuantity(quantity);
            detail.setUnitPrice(product.getPrice());

            order.getOrderDetails().add(detail);
            total += product.getPrice() * quantity;
        }

        order.setTotal(total);
        return orderRepository.save(order);
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
    }

    public List<Order> listAll() {
        return orderRepository.findAll();
    }
}
