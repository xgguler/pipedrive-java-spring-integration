package com.onoff.migration.demo.calllog;

import com.onoff.migration.demo.calllog.model.CallLogRequest;
import com.onoff.migration.demo.calllog.model.CallLogResponse;
import com.onoff.migration.demo.calllog.service.CallLogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pipedrive")
@Validated
public class CallLogController {

    private final CallLogService callLogService;

    public CallLogController(final CallLogService callLogService) {
        this.callLogService = callLogService;
    }

    @GetMapping(value = "/call-log", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CallLogResponse>> getCallLogListByUserId(@RequestParam int userId) {
        return new ResponseEntity<>(callLogService.getByUserId(userId), HttpStatus.OK);
    }

    @PostMapping(value = "/call-log", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCallLog(
            @RequestBody CallLogRequest request
    ) {
        callLogService.create(request);
        return new ResponseEntity<>("Call log created for user.", HttpStatus.OK);
    }
}
