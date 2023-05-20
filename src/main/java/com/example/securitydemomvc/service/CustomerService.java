package com.example.securitydemomvc.service;

import com.example.securitydemomvc.dto.LoginDTO;
import com.example.securitydemomvc.dto.RegisterDTO;
import com.example.securitydemomvc.entity.Customer;
import com.example.securitydemomvc.exception.AuthenticationServiceException;
import com.example.securitydemomvc.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void createCustomer (RegisterDTO registerDTO){
        Customer customer = new Customer(registerDTO.getCustomerEmail(),registerDTO.getPassword1());
        customerRepository.save(customer);
    }

    public void loginCustomer (LoginDTO loginDTO){
        String erroeMessage = "Wrong data";
        Customer customer = customerRepository.findByCustomerEmail(loginDTO.getCustomerEmail())
                .orElseThrow(()-> new AuthenticationServiceException(erroeMessage));
        System.out.println("zalogowany!");
    }
}
