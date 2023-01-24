package com.onoff.migration.demo.oauthcredentials.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "OAUTH_CREDENTIALS")
public class OauthCredentialsModel {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long ID;
    private int USER_ID;
    private String TOKEN;
    @Column(length = 100)
    private String REFRESH_TOKEN;
    private LocalDateTime EXPIRES_IN;
    private LocalDateTime CREATED_DATE;

    public OauthCredentialsModel fromRequestToModel(OauthCredentialsRequest request) {
        OauthCredentialsModel model = new OauthCredentialsModel();
        model.setUSER_ID(request.userId);
        model.setTOKEN(request.token);
        model.setREFRESH_TOKEN(request.refreshToken);
        model.setEXPIRES_IN(LocalDateTime.now().plusMinutes(5));
        model.setCREATED_DATE(LocalDateTime.now());

        return model;
    }
}
