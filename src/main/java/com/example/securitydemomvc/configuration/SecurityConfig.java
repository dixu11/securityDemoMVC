package com.example.securitydemomvc.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {


   /* @Bean
    public SecurityFilterChain getFilterChain(HttpSecurity http) throws Exception {
        //dają dostęp do consoli i własnego html logowania
        http.csrf().disable();
        http.headers().disable();
       return http.authorizeHttpRequests( auth ->
                        auth.requestMatchers("/" , "/register","/login","/console").permitAll()
                                .anyRequest().authenticated()
                //jedna gwiazdka = jeden poziom zagnieżdżenia **- wszystko wgłąb
                )
               .formLogin(Customizer.withDefaults()) //wczytaj Customizer konfigurujący stronę domyślnie
                .build();
    }*/


    //konfiguracja domyślnej strony logowania - klasyczne podejście (depricated!)
    @Bean
    public SecurityFilterChain getFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().disable();

        http.authorizeHttpRequests( auth ->
                                auth.requestMatchers("/" , "/register","/login").permitAll()
                                        .anyRequest().authenticated()
                        //jedna gwiazdka = jeden poziom zagnieżdżenia **- wszystko wgłąb
                )
                .formLogin()
                .loginPage("/login") //jeśli nie użyjemy tego - da domyślną, jeśli użyjemy ustawi własną wskazaną
                .permitAll();
        return http.build();

    }




}
