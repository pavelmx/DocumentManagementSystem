package com.innowise.document.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterForm {

    @Size(min = 3, max = 60)
    private String name;

    @Size(min = 3, max = 60)
    private String username;

    @Size(max = 60)
    @Email
    private String email;

    @Size(min = 6, max = 40)
    private String password;



}
