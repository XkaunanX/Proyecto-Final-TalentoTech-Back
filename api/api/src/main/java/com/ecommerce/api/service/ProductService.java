package com.ecommerce.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.api.entity.Product;
import com.ecommerce.api.exception.ProductNotFoundException;
import com.ecommerce.api.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    // Crear un nuevo producto
    public Product addProduct(Product product) {
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name is mandatory");
        }
        if (product.getName().length() < 3 || product.getName().length() > 100) {
            throw new IllegalArgumentException("Product name must be between 3 and 100 characters");
        }
        if (product.getPrice() == null || product.getPrice() <= 0) {
            throw new IllegalArgumentException("Product price must be greater than 0");
        }
        if (product.getStock() == null || product.getStock() < 0) {
            throw new IllegalArgumentException("Product stock cannot be negative");
        }
        if (product.getDescription() != null && product.getDescription().length() > 255) {
            throw new IllegalArgumentException("Product description can be up to 255 characters");
        }

        return repository.save(product);
    }

    // Listar todos los productos
    public List<Product> listAllProducts() {
        return repository.findAll();
    }

    //Buscar productos por nombre que contenga el término de búsqueda (sin distinguir entre mayúsculas y minúsculas)
    public List<Product> searchProducts(String searchTerm) {
        List<Product> found = repository.findByNameContainingIgnoreCase(searchTerm);

        if (found.isEmpty()) {
            throw new ProductNotFoundException(searchTerm);
        }

        return found;
    }

    // Buscar producto por id o lanzar excepción
    public Product findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id.toString()));
    }

    // Actualizar precio del producto por id
    public Product updatePrice(Long id, Double newPrice) {
        Product product = findById(id);
        product.setPrice(newPrice);
        return repository.save(product);
    }

    // Eliminar producto por id
    public Product deleteProduct(Long id) {
        Product product = findById(id);
        repository.delete(product);
        return product;
    }
}
