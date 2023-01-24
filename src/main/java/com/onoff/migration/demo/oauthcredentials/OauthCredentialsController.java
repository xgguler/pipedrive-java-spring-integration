package com.onoff.migration.demo.oauthcredentials;

import com.onoff.migration.demo.oauthcredentials.model.OauthCredentialsRequest;
import com.onoff.migration.demo.oauthcredentials.service.OauthCredentialsService;
import com.onoff.migration.demo.oauthcredentials.validation.ValidateOauthCredentials;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/setup")
@Validated
public class OauthCredentialsController {

    private final OauthCredentialsService service;
    private final ValidateOauthCredentials validateOauthCredentials;

    public OauthCredentialsController(final OauthCredentialsService service, ValidateOauthCredentials validateOauthCredentials) {
        this.service = service;
        this.validateOauthCredentials = validateOauthCredentials;
    }

    @PostMapping(value = "/auth", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createDeleteCredentials(
            @RequestBody OauthCredentialsRequest request
            ) {
        validateOauthCredentials.validateRequest(request);
        if(Objects.isNull(request.getRefreshToken()) && Objects.isNull(request.getToken())) {
            service.deleteByUserId(request.getUserId());
            return new ResponseEntity<>("User deleted successfully.", HttpStatus.OK);
        }
        service.create(request);
        return new ResponseEntity<>("OAuth credentials created successfully.", HttpStatus.OK);
    }

}
