package com.innowise.document.controller;

import com.innowise.document.entity.User;
import com.innowise.document.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("get/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") Long id){
        User user = userService.getById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("username/{username}")
    public ResponseEntity<User> findUserByUsername(@PathVariable("username") String username){
        User user = userService.findUserByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("email/{email}")
    public ResponseEntity<User> findUserByEmail(@PathVariable("email") String email){
        User user = userService.findUserByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("getall")
    public ResponseEntity<List<User>> getAll(){
        List<User> users = new ArrayList<>();
        userService.getAll().forEach(users::add);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("update")
    public ResponseEntity<User> updateUser(@RequestBody User user, @RequestParam String pass){
        User upuser = userService.updateUser(user, pass);
        return new ResponseEntity<>(upuser, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id){
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("deleteall")
    public ResponseEntity<Void> deleteAll(){
        userService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
