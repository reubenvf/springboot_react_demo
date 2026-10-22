package com.example.demo.service;

import com.example.demo.dto.CustomerRequest;
import com.example.demo.dto.CustomerResponse;
import com.example.demo.dto.PageResponse;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// ReubenVF: Business logic for customer management is kept here to keep controllers lightweight and easier to maintain.
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // ReubenVF: Creates a customer and commits the insert within a transaction for consistency.
    @Transactional
    public CustomerResponse createCustomer(CustomerRequest request) {
        Customer customer = new Customer(request.name(), request.email(), request.status());
        Customer saved = customerRepository.save(customer);
        return mapToResponse(saved);
    }

    // ReubenVF: Updates an existing customer while keeping the entire change atomic.
    @Transactional
    public CustomerResponse updateCustomer(Long id, CustomerRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + id));

        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setStatus(request.status());

        Customer updated = customerRepository.save(customer);
        return mapToResponse(updated);
    }

    // ReubenVF: Fetches one customer by ID for edit or detail screens.
    @Transactional(readOnly = true)
    public CustomerResponse getCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + id));
        return mapToResponse(customer);
    }

    // ReubenVF: Fetches paginated customer records so the client can display lists in chunks of 10 items.
    @Transactional(readOnly = true)
    public PageResponse<CustomerResponse> getCustomers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> pageResult = customerRepository.findAll(pageable);
        List<CustomerResponse> items = pageResult.getContent().stream().map(this::mapToResponse).toList();

        return new PageResponse<>(
                items,
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages(),
                pageResult.isLast()
        );
    }

    // ReubenVF: Maps the database entity to a friendly response payload for API clients.
    private CustomerResponse mapToResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getStatus(),
                customer.getCreatedAt(),
                customer.getUpdatedAt()
        );
    }
}
