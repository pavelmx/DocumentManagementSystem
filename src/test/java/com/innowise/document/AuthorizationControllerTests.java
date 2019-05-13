package com.innowise.document;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.document.entity.User;
import com.innowise.document.repository.UserRepo;
import com.innowise.document.security.JwtResponse;
import com.innowise.document.security.LoginForm;
import com.innowise.document.security.RegisterForm;
import com.innowise.document.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DocumentManagementSystemApplication.class)
@WebAppConfiguration
public class AuthorizationControllerTests {

    private MockMvc mvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    private <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }


    @Test
    public void testAccessUnauthorizedToProtectedResorce() throws Exception {
        String uri = "/user/get/{id}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri, 1L)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(401, status);
    }

    @WithMockUser(value = "admin", password = "123456", roles = "User")
    @Test
    public void testAccessWithIncorrectRole() throws Exception {
        String uri = "/user/deleteall";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(403, status);
    }

    @Test
    public void testLogin() throws Exception {
        String uri = "/auth/login";
        LoginForm loginForm = new LoginForm();
        loginForm.setUsername("admin");
        loginForm.setPassword("123456");
        String inputJson = mapToJson(loginForm);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void testIncorrectLogin() throws Exception {
        String uri = "/auth/login";
        LoginForm loginForm = new LoginForm();
        loginForm.setUsername("fewfew");
        loginForm.setPassword("vgewsgew");
        String inputJson = mapToJson(loginForm);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(401, status);
    }

    @Test
    public void testRegisterWithIncorrectEmail() throws Exception {
        String uri = "/auth/signup";
        RegisterForm regForm = new RegisterForm();
        regForm.setUsername("fewfew");
        regForm.setPassword("vgewsgew");
        regForm.setEmail("jewgkew");
        regForm.setName("gehjwgewj");
        String inputJson = mapToJson(regForm);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void testRegisterExistsUser() throws Exception {
        String uri = "/auth/signup";
        RegisterForm regForm = new RegisterForm();
        regForm.setUsername("admin");
        regForm.setPassword("123456");
        regForm.setEmail("jewgkew");
        regForm.setName("gehjwgewj");
        String inputJson = mapToJson(regForm);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }



}
