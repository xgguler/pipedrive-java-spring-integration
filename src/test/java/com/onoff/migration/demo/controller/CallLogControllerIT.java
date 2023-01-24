package com.onoff.migration.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.onoff.migration.demo.Application;
import com.onoff.migration.demo.calllog.model.CallLogModel;
import com.onoff.migration.demo.calllog.model.CallLogRequest;
import com.onoff.migration.demo.calllog.repository.CallLogRepository;
import com.onoff.migration.demo.calllog.service.CallLogService;
import com.onoff.migration.demo.oauthcredentials.model.OauthCredentialsModel;
import com.onoff.migration.demo.oauthcredentials.respository.OauthCredentialsRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import static org.springframework.http.HttpStatus.OK;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(value = "wiremock")
@SpringBootTest(classes = {Application.class})
@AutoConfigureMockMvc
public class CallLogControllerIT {

    private static WireMockServer wireMockServer;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CallLogService service;

    @MockBean
    private CallLogRepository repository;

    @MockBean
    private OauthCredentialsRepository oauthCredentialsRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void init() {
        wireMockServer = new WireMockServer(
                new WireMockConfiguration()
                        .port(7070)
        );
        wireMockServer.start();
    }

    @AfterAll
    static void stopWireMock() {
        wireMockServer.stop();
    }

    @BeforeEach
    void clearWireMock() {
        wireMockServer.resetAll();
    }

    @Test
    void testWireMock() {
        assertTrue(wireMockServer.isRunning());
    }

    @Test
    void getCallLogList() throws Exception {
        List<CallLogModel> responseList = new ArrayList<>();
        CallLogModel response = new CallLogModel();
        response.setUSER_ID(123);
        response.setIS_SENT(true);
        responseList.add(response);

        Mockito.when(repository.getByUserId(response.getUSER_ID())).thenReturn(responseList);

        mockMvc.perform(get("/pipedrive/call-log?userId=" + response.getUSER_ID())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic " + "dXNlcjpwYXNzd29yZA==")
                .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk());
    }

    @Test
    void createCallLog() throws Exception {
        CallLogRequest request = setCallLogRequest();
        OauthCredentialsModel model = setOauthCredentialsModel(LocalDateTime.now(), LocalDateTime.now());

        Mockito.when(oauthCredentialsRepository.findByUserId(1)).thenReturn(model);

        mockAuthRequest();
        mockCallLogRequest();

        mockMvc.perform(post("/pipedrive/call-log")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("Authorization", "Basic " + "dXNlcjpwYXNzd29yZA==")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk());
    }

    private void mockAuthRequest() {
        wireMockServer.stubFor(WireMock.post(
                "/oauth/token"
        ).willReturn(
                aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody("{ \"access_token\": \"test\"," +
                                " \"token_type\": \"Bearar\"," +
                                " \"expires_in\": 30," +
                                " \"refresh_token\": \"test\"}," +
                                " \"scope\": \"test\"}," +
                                " \"api_domain\": \"https://onoff-demo-sandbox.pipedrive.com\"}")
                        .withStatus(OK.value())
        ));
    }

    private void mockCallLogRequest() {
        wireMockServer.stubFor(WireMock.post(
                "/v1/callLogs"
        ).willReturn(
                aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(OK.value())
        ));
    }

    private CallLogRequest setCallLogRequest() {
        CallLogRequest request = new CallLogRequest();
        request.setUserId(1);
        request.setDuration(30);
        request.setSubject("Phone call request");
        request.setFromNumber("123456789");
        request.setOutcome("connected");

        return request;
    }

    private OauthCredentialsModel setOauthCredentialsModel(LocalDateTime createdDate, LocalDateTime expiresIn) {
        OauthCredentialsModel oauthCredentialsModel = new OauthCredentialsModel();
        oauthCredentialsModel.setUSER_ID(1);
        oauthCredentialsModel.setTOKEN("test");
        oauthCredentialsModel.setREFRESH_TOKEN("test");
        oauthCredentialsModel.setCREATED_DATE(createdDate);
        oauthCredentialsModel.setEXPIRES_IN(expiresIn);

        return oauthCredentialsModel;
    }
}
