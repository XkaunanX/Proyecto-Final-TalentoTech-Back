package com.ecommerce.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.api.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(String name);

}
