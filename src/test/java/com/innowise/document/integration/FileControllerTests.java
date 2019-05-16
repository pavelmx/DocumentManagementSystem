package com.innowise.document.integration;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.document.DocumentManagementSystemApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DocumentManagementSystemApplication.class)
@WebAppConfiguration
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
public class FileControllerTests {

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


    @WithMockUser
    @Test
    public void testDownloadNonexistentFile() throws Exception {
        String filename = "test.txt";
        String uri = "/file/download/{fileName:.+}";
        String kind = mapToJson("credit");
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri, filename)
                .content(kind).accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @WithMockUser
    @Test
    public void testDeleteFileByName() throws Exception {
        String filename = "test.txt";
        String uri = "/file/delete/{fileName:.+}";
        String kind = mapToJson("credit");
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri, filename)
                .content(kind).accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }
}
