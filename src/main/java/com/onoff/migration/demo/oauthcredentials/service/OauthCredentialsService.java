package com.onoff.migration.demo.oauthcredentials.service;

import com.onoff.migration.demo.oauthcredentials.model.OauthCredentialsRequest;

public interface OauthCredentialsService {
    void create(OauthCredentialsRequest model);
    void deleteByUserId(int userId);
}
