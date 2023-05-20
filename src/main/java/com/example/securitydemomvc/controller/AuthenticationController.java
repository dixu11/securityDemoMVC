package com.example.securitydemomvc.controller;

import com.example.securitydemomvc.dto.LoginDTO;
import com.example.securitydemomvc.dto.RegisterDTO;
import com.example.securitydemomvc.exception.AuthenticationServiceException;
import com.example.securitydemomvc.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthenticationController {
    private CustomerService customerService;

    public AuthenticationController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/")
    public String getHomePage() {
        return "index.html";
    }

    @GetMapping("/protected")
    public String getProtectedPage() {
        return "protected.html";
    }

    @GetMapping("/register")
    public ModelAndView getRegisterPage(){
        RegisterDTO registerDTO = new RegisterDTO();
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("registerDTO", registerDTO);
        return modelAndView;
    }

    @PostMapping("/create-customer")
    public ModelAndView createCustomer (@ModelAttribute("registerDTO") RegisterDTO registerDTO){
        ModelAndView modelAndView = new ModelAndView("index.html");
        customerService.createCustomer(registerDTO);
        return modelAndView;
    }

    @GetMapping("/login")
    public String getLoginPage (Model model){
        return "login";
    }

    @PostMapping("/login")
    public String login ( LoginDTO loginDTO, Model model){
        try{
            customerService.loginCustomer(loginDTO);
            model.addAttribute("message", "You are logged in");
        }catch (AuthenticationServiceException e){
            model.addAttribute("message", e.getMessage());
        }
        return "index";
    }
}
