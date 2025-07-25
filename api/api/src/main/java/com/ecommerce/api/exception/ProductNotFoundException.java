package com.ecommerce.api.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super("Product not found: " + message);
    }
}