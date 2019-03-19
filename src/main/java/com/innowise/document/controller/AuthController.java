package com.innowise.document.controller;

import com.innowise.document.entity.User;
import com.innowise.document.security.JwtResponse;
import com.innowise.document.security.LoginForm;
import com.innowise.document.security.RegisterForm;
import com.innowise.document.security.ResponseMessage;
import com.innowise.document.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    UserService userService;

    @PostMapping("signup")
    public ResponseEntity<?> signUpUser(@Valid @RequestBody RegisterForm signup){
        User user = userService.signUpUser(signup);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("login")
    public ResponseEntity<?> logInUser(@Valid @RequestBody LoginForm login) {
        JwtResponse jwtResponse = userService.logInUser(login);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    @GetMapping("/activate/{code}")
    public ResponseEntity<?> activate(@PathVariable String code) {
         boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            return new ResponseEntity<>(new ResponseMessage("User successfully activated"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseMessage("Activation code is not found!"), HttpStatus.NOT_FOUND);
        }
    }
}
