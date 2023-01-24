package com.onoff.migration.demo.oauthcredentials.service.impl;

import com.onoff.migration.demo.oauthcredentials.model.OauthCredentialsModel;
import com.onoff.migration.demo.oauthcredentials.model.OauthCredentialsRequest;
import com.onoff.migration.demo.oauthcredentials.respository.OauthCredentialsRepository;
import com.onoff.migration.demo.oauthcredentials.service.OauthCredentialsService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Component
public class OauthCredentialsServiceImpl implements OauthCredentialsService {

    private final OauthCredentialsRepository repository;

    public OauthCredentialsServiceImpl(final OauthCredentialsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(OauthCredentialsRequest request) {
        try {
            // User is exist or not check.
            OauthCredentialsModel checkUserModel = repository.findByUserId(request.getUserId());
            if (Objects.nonNull(checkUserModel)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            OauthCredentialsModel model = new OauthCredentialsModel().fromRequestToModel(request);
            repository.save(model);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void deleteByUserId(int userId) {
        try {
            repository.deleteByUserId(userId);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
