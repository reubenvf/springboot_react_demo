package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// ReubenVF: Repository interfaces provide the standard CRUD methods for the Product entity without extra boilerplate.
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
