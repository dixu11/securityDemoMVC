package com.example.securitydemomvc.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RegisterDTO {
    private String customerEmail;
    private String password1;
    private String password2;


}
