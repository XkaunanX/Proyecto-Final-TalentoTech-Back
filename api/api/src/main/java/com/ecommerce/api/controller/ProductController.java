package com.ecommerce.api.controller;

import com.ecommerce.api.dto.ProductRequestDTO;
import com.ecommerce.api.dto.ProductResponseDTO;
import com.ecommerce.api.entity.Product;
import com.ecommerce.api.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService productService) {
        this.service = productService;
    }

    private ProductResponseDTO toResponseDTO(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.getDescription()
        );
    }

    private Product toEntity(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setDescription(dto.getDescription());
        return product;
    }

    // Listar todos los productos
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<Product> products = service.listAllProducts();
        List<ProductResponseDTO> response = products.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    // Obtener producto por id
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        Product product = service.findById(id);
        return ResponseEntity.ok(toResponseDTO(product));
    }

    // Crear nuevo producto
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO productDTO) {
        Product product = toEntity(productDTO);
        Product created = service.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponseDTO(created));
    }

    // Actualizar producto completo
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO productDTO) {
        Product existing = service.findById(id);
        existing.setName(productDTO.getName());
        existing.setPrice(productDTO.getPrice());
        existing.setStock(productDTO.getStock());
        existing.setDescription(productDTO.getDescription());
        Product updated = service.addProduct(existing);
        return ResponseEntity.ok(toResponseDTO(updated));
    }

    // Actualizar parcialmente
    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> partiallyUpdateProduct(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Product existing = service.findById(id);

        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    existing.setName((String) value);
                    break;
                case "price":
                    if (value instanceof Number) {
                        existing.setPrice(((Number) value).doubleValue());
                    }
                    break;
                case "stock":
                    if (value instanceof Number) {
                        existing.setStock(((Number) value).longValue());
                    }
                    break;
                case "description":
                    existing.setDescription((String) value);
                    break;
            }
        });

        Product updated = service.addProduct(existing);
        return ResponseEntity.ok(toResponseDTO(updated));
    }

    // Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
