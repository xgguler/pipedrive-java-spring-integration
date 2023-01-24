package com.onoff.migration.demo.oauthcredentials.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OauthCredentialsRequest {
    @JsonProperty(value = "userId")
    public int userId;

    @JsonProperty(value = "token")
    public String token;

    @JsonProperty(value = "refreshToken")
    public String refreshToken;
}
