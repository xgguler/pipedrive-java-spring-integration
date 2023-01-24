package com.onoff.migration.demo.calllog.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.onoff.migration.demo.calllog.model.CallLogModel;
import com.onoff.migration.demo.calllog.model.CallLogRequest;
import com.onoff.migration.demo.calllog.model.CallLogResponse;
import com.onoff.migration.demo.calllog.repository.CallLogRepository;
import com.onoff.migration.demo.calllog.service.CallLogService;
import com.onoff.migration.demo.client.WebServiceClient;
import com.onoff.migration.demo.client.model.OauthTokenModel;
import com.onoff.migration.demo.oauthcredentials.model.OauthCredentialsModel;
import com.onoff.migration.demo.oauthcredentials.respository.OauthCredentialsRepository;
import com.onoff.migration.demo.oauthcredentials.validation.ValidateOauthCredentials;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class CallLogServiceImpl implements CallLogService {

    private final CallLogRepository repository;

    private final OauthCredentialsRepository credentialsRepository;

    private final ValidateOauthCredentials validateOauthCredentials;

    private final WebServiceClient client;

    public CallLogServiceImpl(final CallLogRepository repository,
                              final OauthCredentialsRepository credentialsRepository,
                              final ValidateOauthCredentials validateOauthCredentials,
                              final WebServiceClient client) {
        this.repository = repository;
        this.credentialsRepository = credentialsRepository;
        this.validateOauthCredentials = validateOauthCredentials;
        this.client = client;
    }

    @Override
    public void create(CallLogRequest request) {
        try {
            OauthCredentialsModel model = validateOauthCredentials.validateUserId(request.getUserId());
            int success;
            if (!model.getEXPIRES_IN().isBefore(model.getCREATED_DATE())) {
                OauthTokenModel oauthTokenModel = client.getAccessToken(request.getUserId());
                credentialsRepository.updateTokenByUserId(request.getUserId(),
                        oauthTokenModel.getAccess_token(),
                        LocalDateTime.now().plusMinutes(5),
                        LocalDateTime.now()
                );
                model = credentialsRepository.findByUserId(request.getUserId());
            }
            success = client.createCallLog(request, model.getTOKEN());
            CallLogModel callLogModel = new CallLogModel().fromRequestToModel(request);
            callLogModel.setIS_SENT(success == 200);
            repository.save(callLogModel);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<CallLogResponse> getByUserId(int userId) {
        try {
            List<CallLogModel> callLogModelList = repository.getByUserId(userId);
            return new CallLogModel().fromModelToResponse(callLogModelList);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
