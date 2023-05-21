package com.example.securitydemomvc.service;

import com.example.securitydemomvc.dto.RegisterDTO;
import com.example.securitydemomvc.entity.Customer;
import com.example.securitydemomvc.exception.AuthenticationServiceException;
import com.example.securitydemomvc.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;

@Service
@Transactional
public class CustomerService  implements UserDetailsService { //WYMAGANE ABY SPRING WIDZIAL TA KLASE JAKO TAKA KTORA DAJE UZYTKOWNIKOW
    private CustomerRepository customerRepository;
    //powiniśmy kodować hasła przed dodaniem obiektu do bazy danych
    private PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createCustomer (RegisterDTO registerDTO){
        String encodedPassword = passwordEncoder.encode(registerDTO.getPassword1());
        Customer customer = new Customer(registerDTO.getCustomerEmail(), encodedPassword); // !!!!!!
        customerRepository.save(customer);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Wyszukuję...");
        Customer customer = customerRepository.findByCustomerEmail(username).orElseThrow(() -> new UsernameNotFoundException("not found"));
        System.out.println("Logujesz się na: "+ customer);
       // return new User(customer.getCustomerEmail(), customer.getPassword(), new ArrayList<>());

            //powoduje circular!!
          return User.withUsername(customer.getCustomerEmail())
                .password(customer.getPassword())
                .roles("moderator") // podstawianie przechowywanej roli z bazy danych zrobić
                .build();
    }
}
