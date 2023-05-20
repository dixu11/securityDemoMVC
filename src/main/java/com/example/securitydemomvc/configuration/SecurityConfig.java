package com.example.securitydemomvc.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    //przygotowuje narzedzie do kodowania hasla
    @Bean
    public PasswordEncoder getEncoder() {
       return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager getUserDetailsManager() {
        //tworze obiekt uzytkownika
        UserDetails user1 = User.withUsername("admin")
                .password(getEncoder().encode( "aaa")) // koduje haslo
                .roles("moderator")
                .build();

        UserDetails user2 = User.withUsername("adam")
                .password(getEncoder().encode( "bbb")) // koduje haslo
                .roles("user")
                .build();


        //tworzę obiekt zarzadzajacy uzytkownikami
        return new InMemoryUserDetailsManager(user1,user2);
    }


    @Bean
    public SecurityFilterChain getFilterChain(HttpSecurity http) throws Exception {
        //dają dostęp do consoli i własnego html logowania
        http.csrf().disable();
        http.headers().disable();
       return http.authorizeHttpRequests( auth ->
                        auth.requestMatchers("/" , "/register","/login","/console").permitAll()
                               // .anyRequest().authenticated()
                                .anyRequest().hasRole("moderator")
                //jedna gwiazdka = jeden poziom zagnieżdżenia **- wszystko wgłąb
                )
//               .form(Customizer.withDefaults())  //w przypadku domyślnej strony logowania
               .formLogin(
                       customizer -> customizer.loginPage("/login")//gdzie nasz formularz logowania
                            //   .successForwardUrl("/") // sprawdzić
                               .permitAll()
                                // gdzie idziemy po zalogowaniu
               ) //wczytaj Customizer konfigurujący stronę domyślnie
               .logout(Customizer.withDefaults())
                .build();
    }


    //konfiguracja domyślnej strony logowania - klasyczne podejście (depricated!)
 /*   @Bean
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

    }*/




}
