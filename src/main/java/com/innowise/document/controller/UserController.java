package com.innowise.document.controller;

import com.innowise.document.entity.Role;
import com.innowise.document.entity.RoleName;
import com.innowise.document.entity.User;
import com.innowise.document.repository.UserRepo;
import com.innowise.document.security.JwtResponse;
import com.innowise.document.security.LoginForm;
import com.innowise.document.security.RegisterForm;
import com.innowise.document.security.ResponseMessage;
import com.innowise.document.service.RoleService;
import com.innowise.document.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleService roleService;

    @GetMapping("get/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") Long id) {
         User user = userService.getById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("username/{username}")
    public ResponseEntity<User> findUserByUsername(@PathVariable("username") String username) {
        User user = userService.findUserByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("email/{email}")
    public ResponseEntity<User> findUserByEmail(@PathVariable("email") String email) {
        User user = userService.findUserByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("getall")
    public ResponseEntity<List<User>> getAll() {
        List<User> users = new ArrayList<>();
        userService.getAll().forEach(users::add);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("update")
    public ResponseEntity<?> updateUser(User user) {
        return null;
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
