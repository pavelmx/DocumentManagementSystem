package com.innowise.document.service;

import com.innowise.document.entity.Document;
import com.innowise.document.entity.Role;
import com.innowise.document.entity.RoleName;
import com.innowise.document.entity.User;
import com.innowise.document.repository.UserRepo;
import com.innowise.document.security.JwtProvider;
import com.innowise.document.security.JwtResponse;
import com.innowise.document.security.LoginForm;
import com.innowise.document.security.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleService roleService;

    @Autowired
    DocumentService documentService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    MailSenderServiceImpl mailSenderService;

    @Override
    public User getById(Long id){
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User with id:'" + id + "' not found."));
    }

    @Override
    public User findUserByUsername(String username){
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User with username: '" + username + "' not found."));
    }

    @Override
    public User findUserByEmail(String email){
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User with email: '" + email + "' not found."));
    }

    @Override
    public List<User> getAll(){
        List<User> users = new ArrayList<>();
        userRepo.findAll().forEach(users::add);
        return users;
    }

    @Override
    public void deleteById(Long id){
        List<Document> docs = documentService.findAllDocumentsByUserId(id);
        docs.forEach(doc -> documentService.deleteById(doc.getId()));
        userRepo.deleteById(id);
    }

    @Override
    public boolean existsById(Long id){
        return userRepo.existsById(id);
    }

    @Override
    public boolean existsByUsername(String username){
        return userRepo.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email){
        return userRepo.existsByEmail(email);
    }

    @Override
    public User updateUser(User user, String password){
        if (!existsById(user.getId())) {
            throw new EntityNotFoundException("User with username: '" + user.getUsername() + "' not found.");
        }
        if (encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("New password can't be old password!");
        }
        user.setPassword(encoder.encode(password));
        String message = "Hello, " + user.getUsername() + "!\n " +
                "Your password was successfully changed. New password - " + password;
        mailSenderService.send(user.getEmail(), "Change password", message);
        return userRepo.save(user);
    }

    @Override
    public User signUpUser(RegisterForm signup){
        if (existsByUsername(signup.getUsername()))
            throw new EntityExistsException("User with username: '" + signup.getUsername() + "' exists.");
        if (existsByEmail(signup.getEmail()))
            throw new EntityExistsException("User with email: '" + signup.getEmail() + "' exists.");

        User newuser = new User(signup.getUsername(), encoder.encode(signup.getPassword()),
                signup.getEmail(), signup.getName() );
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findByName(RoleName.ROLE_USER));
        newuser.setRoles(roles);
        newuser.setActivationCode(UUID.randomUUID().toString());
        userRepo.save(newuser);

        if (!StringUtils.isEmpty(newuser.getEmail())) {
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
        if (user.isAccountNonLocked())
            return new JwtResponse(jwt, user.getUsername(), user.getAuthorities());
        else
            return null;
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

    private List<User> getAllUsersByRoleUser(){
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findByName(RoleName.ROLE_USER));
        List<User> users = userRepo.findByRoles(roles);
        return users;
    }

    @Override
    public void deleteAll(){
        List<User> users = getAllUsersByRoleUser();
        users.forEach(user -> deleteById(user.getId()));
    }
}