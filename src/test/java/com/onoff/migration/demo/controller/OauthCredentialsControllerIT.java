package com.onoff.migration.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onoff.migration.demo.Application;
import com.onoff.migration.demo.oauthcredentials.model.OauthCredentialsModel;
import com.onoff.migration.demo.oauthcredentials.model.OauthCredentialsRequest;
import com.onoff.migration.demo.oauthcredentials.respository.OauthCredentialsRepository;
import com.onoff.migration.demo.oauthcredentials.service.OauthCredentialsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {Application.class})
@AutoConfigureMockMvc
public class OauthCredentialsControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OauthCredentialsService service;

    @MockBean
    private OauthCredentialsRepository repository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createCredentials() throws Exception {
        OauthCredentialsRequest request = new OauthCredentialsRequest();
        request.setUserId(12345);
        request.setToken("test");
        request.setRefreshToken("test");

        String jsonData = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/setup/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic " + "dXNlcjpwYXNzd29yZA==")
                .content(jsonData)
                .accept(MediaType.APPLICATION_JSON)).andExpectAll(status().isOk());

    }

    @Test
    void createCredentialsErrorEmptyUserId() throws Exception {
        OauthCredentialsRequest request = new OauthCredentialsRequest();
        request.setToken("test");
        request.setRefreshToken("test");

        String jsonData = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/setup/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic " + "dXNlcjpwYXNzd29yZA==")
                .content(jsonData)
                .accept(MediaType.APPLICATION_JSON)).andExpectAll(status().isBadRequest());

    }

    @Test
    void createCredentialsErrorUserAlreadyExist() throws Exception {
        OauthCredentialsRequest request = new OauthCredentialsRequest();
        request.setUserId(1);
        request.setToken("test");
        request.setRefreshToken("test");

        OauthCredentialsModel model = new OauthCredentialsModel();
        model.setUSER_ID(1);

        Mockito.when(repository.findByUserId(1)).thenReturn(model);

        String jsonData = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/setup/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic " + "dXNlcjpwYXNzd29yZA==")
                .content(jsonData)
                .accept(MediaType.APPLICATION_JSON)).andExpectAll(status().isBadRequest());

    }

    @Test
    void createCredentialsErrorEmptyToken() throws Exception {
        OauthCredentialsRequest request = new OauthCredentialsRequest();
        request.setUserId(1);
        request.setRefreshToken("test");

        String jsonData = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/setup/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic " + "dXNlcjpwYXNzd29yZA==")
                .content(jsonData)
                .accept(MediaType.APPLICATION_JSON)).andExpectAll(status().isBadRequest());

    }

    @Test
    void createCredentialsErrorEmptyRefreshToken() throws Exception {
        OauthCredentialsRequest request = new OauthCredentialsRequest();
        request.setUserId(1);
        request.setToken("test");

        String jsonData = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/setup/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic " + "dXNlcjpwYXNzd29yZA==")
                .content(jsonData)
                .accept(MediaType.APPLICATION_JSON)).andExpectAll(status().isBadRequest());

    }

    @Test
    void createDeleteCredentials() throws Exception {
        OauthCredentialsRequest request = new OauthCredentialsRequest();
        request.setUserId(12345);

        String jsonData = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/setup/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic " + "dXNlcjpwYXNzd29yZA==")
                .content(jsonData)
                .accept(MediaType.APPLICATION_JSON)).andExpectAll(status().isOk());

    }

}
