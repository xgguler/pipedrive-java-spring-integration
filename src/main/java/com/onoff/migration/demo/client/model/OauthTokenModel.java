package com.onoff.migration.demo.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OauthTokenModel {

    @JsonProperty("access_token")
    private String access_token;

    @JsonProperty("token_type")
    private String token_type;

    @JsonProperty("refresh_token")
    private String refresh_token;

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("expires_in")
    private int expires_in;

    @JsonProperty("api_domain")
    private String api_domain;
}
