package com.onoff.migration.demo.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.onoff.migration.demo.calllog.model.CallLogRequest;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateCallLogModel {

    @JsonProperty("userId")
    private int userId;

    @JsonProperty("activity_id")
    private int activity_id;

    @JsonProperty("outcome")
    private String outcome;

    @JsonProperty("to_phone_number")
    private String to_phone_number;

    @JsonProperty("start_time")
    private String start_time;

    @JsonProperty("end_time")
    private String end_time;

    public CreateCallLogModel fromRequestToModel(CallLogRequest request) {
        CreateCallLogModel model = new CreateCallLogModel();
        model.setUserId(request.getUserId());
        model.setActivity_id(1);
        model.setOutcome(request.getOutcome());
        model.setTo_phone_number(request.getFromNumber());
        model.setStart_time(LocalDateTime.now().toString());
        model.setEnd_time(LocalDateTime.now().plusMinutes(5).toString());

        return model;
    }
}
