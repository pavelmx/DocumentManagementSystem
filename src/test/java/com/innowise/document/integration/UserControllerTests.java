package com.innowise.document.integration;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.document.DocumentManagementSystemApplication;
import com.innowise.document.entity.User;
import com.innowise.document.repository.UserRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DocumentManagementSystemApplication.class)
@WebAppConfiguration
public class UserControllerTests {

    private MockMvc mvc;

    @Autowired
    private UserRepo userRepo;

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



    @WithMockUser
    @Test
    public void testGetUserById() throws Exception {
        String uri = "/user/get/{id}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri, 1L)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @WithMockUser
    @Test
    public void testFindUserByUsername() throws Exception {
        String uri = "/user/username/{username}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri, "admin")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void testFindUserByEmail() throws Exception {
        String uri = "/user/email/{email}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri, "admin@admin")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }



    @WithMockUser(roles = "ADMIN")
    @Test
    public void testGetUsersList() throws Exception {
        String uri = "/user/getall";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        User[] userList = mapFromJson(content, User[].class);
        assertTrue(userList.length > 0);
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void testDeleteUser() throws Exception {
        String uri = "/user/delete/{id}";
        User user = userRepo.save(new User( "usergwname", "namee", "123ge456","emegwail@email"));
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri, user.getId())).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

    }
}
