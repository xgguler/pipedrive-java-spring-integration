package com.onoff.migration.demo.calllog.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CallLogResponse {

    @JsonProperty("id")
    private long id;

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

    @JsonProperty("isSent")
    private boolean isSent;

    @JsonProperty("createdDate")
    private LocalDateTime createdDate;

    public CallLogResponse(long id, int userId, String subject, int duration, String outcome, String fromNumber, Boolean isSent, LocalDateTime createdDate) {
        this.id = id;
        this.userId = userId;
        this.subject = subject;
        this.duration = duration;
        this.outcome = outcome;
        this.fromNumber = fromNumber;
        this.isSent = isSent;
        this.createdDate = createdDate;
    }
}
