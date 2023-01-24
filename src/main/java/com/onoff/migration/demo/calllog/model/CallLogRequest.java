package com.onoff.migration.demo.calllog.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CallLogRequest {
    @JsonProperty("userId")
    private int userId;

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("duration")
    private int duration;

    @JsonProperty("outcome")
    private String outcome;

    @JsonProperty("fromNumber")
    private String fromNumber;
}
