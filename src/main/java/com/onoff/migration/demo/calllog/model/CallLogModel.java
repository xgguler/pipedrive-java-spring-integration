package com.onoff.migration.demo.calllog.model;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "CALL_LOG")
public class CallLogModel {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long ID;
    private int USER_ID;
    @Column(length = 40)
    private String SUBJECT;
    private int DURATION;
    @Column(length = 40)
    private String OUTCOME;
    @Column(length = 40)
    private String FROM_NUMBER;
    private Boolean IS_SENT;
    private LocalDateTime CREATED_DATE;

    public CallLogModel fromRequestToModel(CallLogRequest request) {
        CallLogModel model = new CallLogModel();
        model.setUSER_ID(request.getUserId());
        model.setSUBJECT(request.getSubject());
        model.setDURATION(request.getDuration());
        model.setOUTCOME(request.getOutcome());
        model.setFROM_NUMBER(request.getFromNumber());
        model.setCREATED_DATE(LocalDateTime.now());

        return model;
    }

    public List<CallLogResponse> fromModelToResponse(List<CallLogModel> modelList) {

        return modelList.stream().map(
                data -> new CallLogResponse(data.getID(), data.getUSER_ID(),
                        data.getSUBJECT(), data.getDURATION(), data.getOUTCOME(),
                        data.getFROM_NUMBER(), data.getIS_SENT(), data.getCREATED_DATE())
        ).toList();
    }
}
