package com.innowise.document.unit;

import com.innowise.document.entity.Role;
import com.innowise.document.entity.User;
import com.innowise.document.repository.RoleRepo;
import com.innowise.document.repository.UserRepo;
import com.innowise.document.security.RegisterForm;
import com.innowise.document.service.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.MockitoAnnotations.initMocks;



public class UserServiceTests {

    @Mock
    private UserRepo mockUserRepository;

    @Mock
    private RoleServiseImpl mockRoleService;

    @Mock
    private BCryptPasswordEncoder mockBCryptPasswordEncoder;

    @Mock
    private MailSenderServiceImpl mailSenderService;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @Before
    public void setUp() {
        initMocks(this);
        Set< Role > roles = new HashSet<>();
        user = new User(1L, "testuser", "user12345",
               "123456", "test@user.com","code", "adress", roles);
    }

    @Test
    public void testFindUserByEmail() {
        Mockito.when(mockUserRepository.findByEmail(anyString())).thenReturn(java.util.Optional.ofNullable(user));
         String email = "test@user.com";
         User result = userService.findUserByEmail(email);
        assertEquals(email, result.getEmail());
    }

    @Test
    public void testFindUserByUsername() {
        Mockito.when(mockUserRepository.findByUsername(anyString())).thenReturn(java.util.Optional.ofNullable(user));
       String username = "testuser";
         User result = userService.findUserByUsername(username);
        assertEquals(username, result.getUsername());
    }

    @Test
    public void testSignUpUser() {
        Role role = new Role();
        Mockito.when(mockRoleService.findByName(any())).thenReturn(role);
        Mockito.when(mockUserRepository.save(any())).thenReturn(user);
        String name = "user12345";
        String username = "testuser";
        String email = "test@user.com";
        String password = "123456";
        RegisterForm registerForm = new RegisterForm(name, username, email, password);
        User result = userService.signUpUser(registerForm);
        assertEquals(email, result.getEmail());
        assertEquals(username, result.getUsername());
        assertEquals(name, result.getName());
    }

    @Test
    public void testUpdateUser() {
        Mockito.when(mockUserRepository.existsById(any())).thenReturn(true);
        Mockito.when(mockUserRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(user));
        Mockito.when(mockUserRepository.save(any())).thenReturn(user);
        String name = "user12345";
        String email = "test@user.com";
        User result = userService.updateUser(user);
        assertEquals(email, result.getEmail());
        assertEquals(name, result.getName());
    }

    @Test
    public void testActivateUser() {
        Mockito.when(mockUserRepository.findByActivationCode(anyString())).thenReturn(java.util.Optional.ofNullable(user));
         String code = "nfjnwegnewbgwbgfwoe"; //incorrect code
         boolean result = userService.activateUser(code);
        assertEquals(true, result);
    }
}
