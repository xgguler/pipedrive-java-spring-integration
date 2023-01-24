package com.onoff.migration.demo.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CallLogData {

    @JsonProperty("id")
    private String id;

    @JsonProperty("activity_id")
    private int activity_id;

    @JsonProperty("org_id")
    private int org_id;

    @JsonProperty("person_id")
    private int person_id;

    @JsonProperty("deal_id")
    private int deal_id;

    @JsonProperty("duration")
    private String duration;

    @JsonProperty("outcome")
    private String outcome;

    @JsonProperty("to_phone_number")
    private String to_phone_number;

    @JsonProperty("has_recording")
    private String has_recording;

    @JsonProperty("start_time")
    private String start_time;

    @JsonProperty("end_time")
    private String end_time;

    @JsonProperty("company_id")
    private int company_id;

    @JsonProperty("user_id")
    private int user_id;

}
