package com.innowise.document.integration;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.document.entity.documents.CreditContract;
import com.innowise.document.repository.UserRepo;
import com.innowise.document.repository.documents.CreditContractRepo;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.Date;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
public class DocumentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CreditContractRepo repository;

    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    private <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }



    @WithMockUser(value = "admin", password = "123456")
    @Test
    public void testGetById()
            throws Exception {

        mockMvc.perform(get("/credit-contract/get/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.clientFullName").value("Ivanov Ivan Ivanovich"))
                .andExpect(jsonPath("$.dateOfCreation").value("2018-11-06"))
                .andExpect(jsonPath("$.term").value(31))
                .andExpect(jsonPath("$.active").value(false));
    }

    @WithMockUser(value = "admin", password = "123456")
    @Test
    public void testGetAllByUsername()
            throws Exception {

        mockMvc.perform(get("/credit-contract/get-username/{username}", "admin")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk());
    }

    @WithMockUser(value = "admin", password = "123456")
    @Test
    public void testCreateDocumentByUsernameWithoutBody()
            throws Exception {

        mockMvc.perform(post("/credit-contract/add/{username}", "admin")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(value = "admin", password = "123456")
    @Test
    public void testCreateDocumentByUsername()
            throws Exception {
        CreditContract creditContract = new CreditContract();
        creditContract.setAnnualInterest(12f);
        creditContract.setTerm(10);
        creditContract.setCreditAmount(1000f);
        String input = mapToJson(creditContract);
        mockMvc.perform(post("/credit-contract/add/{username}", "admin")
                .content(input)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isCreated());
    }

    @After
    public void afterTests(){
        repository.deleteAll();
    }

}
