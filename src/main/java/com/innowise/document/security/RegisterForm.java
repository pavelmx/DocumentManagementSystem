package com.innowise.document.security;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
