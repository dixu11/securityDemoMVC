package com.example.securitydemomvc.repository;

import com.example.securitydemomvc.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository <Customer, Integer>{

    Optional<Customer> findByCustomerEmail(String customerEmail);
}
