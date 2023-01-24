package com.onoff.migration.demo.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onoff.migration.demo.calllog.model.CallLogRequest;
import com.onoff.migration.demo.client.model.CreateCallLogModel;
import com.onoff.migration.demo.client.model.CreateCallLogResponse;
import com.onoff.migration.demo.client.model.OauthTokenModel;
import com.onoff.migration.demo.oauthcredentials.model.OauthCredentialsModel;
import com.onoff.migration.demo.oauthcredentials.validation.ValidateOauthCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import static com.onoff.migration.demo.client.util.Common.*;

@Service
public class WebServiceClient {

    private final RestTemplate restTemplate;

    private final ObjectMapper mapper;

    private final ValidateOauthCredentials validateOauthCredentials;

    @Value("${pipedrive-api.auth-url}")
    public String OAUTH_TOKEN_URL;

    @Value("${pipedrive-api.call-log-url}")
    public String CALL_LOG_URL;

    public WebServiceClient(final RestTemplate restTemplate, ValidateOauthCredentials validateOauthCredentials) {
        this.restTemplate = restTemplate;
        this.validateOauthCredentials = validateOauthCredentials;
        this.mapper = new ObjectMapper();
    }

    public OauthTokenModel getAccessToken(int userId) {
        try {
            OauthCredentialsModel model = validateOauthCredentials.validateUserId(userId);
            String refreshToken = model.getREFRESH_TOKEN();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Basic " + BASE_CREDENTIALS);
            headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("refresh_token", refreshToken);
            map.add("grant_type", "refresh_token");

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

            ResponseEntity<OauthTokenModel> response =
                    restTemplate.exchange(OAUTH_TOKEN_URL,
                            HttpMethod.POST,
                            entity,
                            OauthTokenModel.class);

            return response.getBody();
        } catch (HttpStatusCodeException e) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }
    }

    public int createCallLog(CallLogRequest request, String token) throws JsonProcessingException {
        try {
            CreateCallLogModel createCallLogModel = new CreateCallLogModel().fromRequestToModel(request);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

            String convertToJson = mapper.writeValueAsString(createCallLogModel);

            HttpEntity<String> entity = new HttpEntity<>(convertToJson ,headers);

            ResponseEntity<CreateCallLogResponse> response =
                    restTemplate.exchange(CALL_LOG_URL,
                            HttpMethod.POST,
                            entity,
                            CreateCallLogResponse.class);

            return response.getStatusCode().value();
        } catch (HttpClientErrorException e) {
            return e.getStatusCode().value();
        }
    }
}
