package com.project.myproject.dao;

import com.project.myproject.entity.Report;
import com.project.myproject.enums.EModerationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query(value = "select r from Report r where r.task.id = :taskId and r.user.username like :username")
    List<Report> findAllByTaskIdAndUsername(long taskId, String username);

    @Query(value = "select r from Report r join fetch r.status s where r.id = :id and r.user.username like :username and s.name in (:status)")
    Report findByTaskIdAndUsernameAndStatusIn(long id, String username, List<EModerationStatus> status);

}
