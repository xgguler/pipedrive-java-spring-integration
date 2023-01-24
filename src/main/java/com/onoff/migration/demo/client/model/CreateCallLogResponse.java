package com.onoff.migration.demo.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateCallLogResponse {

    @JsonProperty("success")
    private String success;

    @JsonProperty("data")
    private CallLogData data;
}
