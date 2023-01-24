package com.onoff.migration.demo.calllog.service;

import com.onoff.migration.demo.calllog.model.CallLogRequest;
import com.onoff.migration.demo.calllog.model.CallLogResponse;

import java.util.List;

public interface CallLogService {

    void create(CallLogRequest request);
    List<CallLogResponse> getByUserId(int userId);
}
