package com.innowise.document.service;

import com.innowise.document.entity.User;
import com.innowise.document.security.JwtResponse;
import com.innowise.document.security.LoginForm;
import com.innowise.document.security.RegisterForm;


public interface UserService extends RestService<User> {

    User findUserByUsername(String username);

    User findUserByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User updateUser(User user);

    User signUpUser(RegisterForm signup); //add user

    JwtResponse logInUser(LoginForm login);

    boolean activateUser(String code);

    void deleteAll();
}
