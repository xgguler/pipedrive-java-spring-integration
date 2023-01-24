package com.onoff.migration.demo.oauthcredentials.validation;

import com.onoff.migration.demo.oauthcredentials.model.OauthCredentialsModel;
import com.onoff.migration.demo.oauthcredentials.model.OauthCredentialsRequest;

public interface ValidateOauthCredentials {

    void validateRequest(OauthCredentialsRequest request);

    OauthCredentialsModel validateUserId(int userId);
}
