package com.example.demo.service;

import com.example.demo.dto.ProductRequest;
import com.example.demo.dto.ProductResponse;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// ReubenVF: This service holds the product business rules and keeps the controller focused on HTTP concerns.
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // ReubenVF: Creates a new product and saves it in a single database transaction.
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        Product product = new Product(
                request.name(),
                request.description(),
                request.quantity(),
                request.price()
        );
        Product saved = productRepository.save(product);
        return ProductMapper.toResponse(saved);
    }

    // ReubenVF: Updates an existing product while keeping the change atomic and consistent.
    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));

        product.setName(request.name());
        product.setDescription(request.description());
        product.setQuantity(request.quantity());
        product.setPrice(request.price());

        Product updated = productRepository.save(product);
        return ProductMapper.toResponse(updated);
    }

    // ReubenVF: Reads all products from storage and converts them into API-friendly response objects.
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(ProductMapper::toResponse).toList();
    }

    // ReubenVF: Returns one product when the client needs detailed information or wants to edit it.
    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
        return ProductMapper.toResponse(product);
    }

    // ReubenVF: Deletes a product after confirming it exists in the database.
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
        productRepository.delete(product);
    }
}
