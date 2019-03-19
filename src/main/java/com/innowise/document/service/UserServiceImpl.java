package com.innowise.document.service;

import com.innowise.document.entity.Role;
import com.innowise.document.entity.RoleName;
import com.innowise.document.entity.User;
import com.innowise.document.repository.UserRepo;
import com.innowise.document.security.JwtProvider;
import com.innowise.document.security.JwtResponse;
import com.innowise.document.security.LoginForm;
import com.innowise.document.security.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleService roleService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    MailSenderServiceImpl mailSenderService;


    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User getById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User with id = " + id + " not found."));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User findUserByUsername(String username){
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User with username = " + username + " not found."));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User findUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User with email = " + email + " not found."));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        userRepo.findAll().forEach(users::add);
        return users;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteById(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return userRepo.existsById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepo.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    public User signUpUser(RegisterForm signup){
        User newuser = new User(signup.getUsername(), encoder.encode(signup.getPassword()),
                                signup.getEmail(), signup.getName());
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findByName(RoleName.ROLE_USER));
        newuser.setRoles(roles);
        newuser.setActivationCode(UUID.randomUUID().toString());
        userRepo.save(newuser);

        if(!StringUtils.isEmpty(newuser.getEmail())) {
            String message = "Hello, " + newuser.getUsername() + "!\n " +
                    "Please confirm your mail: http://localhost:8080/auth/activate/" + newuser.getActivationCode();
            mailSenderService.send(newuser.getEmail(), "Activation code", message);
        }

        return newuser;
    }

    @Override
    public JwtResponse logInUser(LoginForm login){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails user = (UserDetails) authentication.getPrincipal();
        return new JwtResponse(jwt, user.getUsername(), user.getAuthorities());
    }

    @Override
    public boolean activateUser(String code){
        User user = userRepo.findByActivationCode(code).get();

        if (user == null) {
            return false;
        }
        user.setActivationCode(null);
        userRepo.save(user);
        return true;
    }
}