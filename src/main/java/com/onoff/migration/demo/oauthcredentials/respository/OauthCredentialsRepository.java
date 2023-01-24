package com.onoff.migration.demo.oauthcredentials.respository;

import com.onoff.migration.demo.oauthcredentials.model.OauthCredentialsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface OauthCredentialsRepository extends JpaRepository<OauthCredentialsModel, Long> {

    @Modifying
    @Transactional
    @Query(value = "delete from OAUTH_CREDENTIALS where USER_ID= :userId", nativeQuery = true)
    void deleteByUserId(@Param("userId") int userId);

    @Transactional
    @Query(value = "select * from OAUTH_CREDENTIALS where USER_ID= :userId", nativeQuery = true)
    OauthCredentialsModel findByUserId(@Param("userId") int userId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = "update OAUTH_CREDENTIALS set TOKEN= :token, EXPIRES_IN= :expiresIn, CREATED_DATE= :createdDate where USER_ID= :userId", nativeQuery = true)
    void updateTokenByUserId(@Param("userId") int userId,
                                              @Param("token") String token,
                                              @Param("expiresIn") LocalDateTime expiresIn,
                                              @Param("createdDate") LocalDateTime createdDate
    );
}
