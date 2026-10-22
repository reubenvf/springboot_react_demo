package com.example.demo.repository;

import com.example.demo.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// ReubenVF: Repository interfaces handle database access and standard CRUD behavior without extra boilerplate.
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
