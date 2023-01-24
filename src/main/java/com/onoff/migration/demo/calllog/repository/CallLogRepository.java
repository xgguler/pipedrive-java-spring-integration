package com.onoff.migration.demo.calllog.repository;

import com.onoff.migration.demo.calllog.model.CallLogModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CallLogRepository extends JpaRepository<CallLogModel, Long> {

    @Transactional
    @Query(value = "select * from CALL_LOG where USER_ID= :userId", nativeQuery = true)
    List<CallLogModel> getByUserId(@Param("userId") int userId);
}
