package com.onoff.migration.demo.oauthcredentials.validation.impl;

import com.onoff.migration.demo.oauthcredentials.model.OauthCredentialsModel;
import com.onoff.migration.demo.oauthcredentials.model.OauthCredentialsRequest;
import com.onoff.migration.demo.oauthcredentials.respository.OauthCredentialsRepository;
import com.onoff.migration.demo.oauthcredentials.validation.ValidateOauthCredentials;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Component
public class ValidateOauthCredentialsImpl implements ValidateOauthCredentials {

    private final OauthCredentialsRepository repository;

    public ValidateOauthCredentialsImpl(OauthCredentialsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validateRequest(OauthCredentialsRequest request) {
        if(request.getUserId() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else if(Objects.nonNull(request.getToken())
                && Objects.isNull(request.getRefreshToken())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else if(Objects.isNull(request.getToken())
                && Objects.nonNull(request.getRefreshToken())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public OauthCredentialsModel validateUserId(int userId) {
        OauthCredentialsModel model = repository.findByUserId(userId);
        if(Objects.isNull(model)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return model;
    }
}
