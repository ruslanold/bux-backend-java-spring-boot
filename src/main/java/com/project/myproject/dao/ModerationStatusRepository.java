package com.project.myproject.dao;

import com.project.myproject.entity.ModerationStatus;
import com.project.myproject.enums.EModerationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ModerationStatusRepository extends JpaRepository<ModerationStatus, Integer> {
    @Query(value = "select s from ModerationStatus s where s.name = :status")
    ModerationStatus findByName(EModerationStatus status);
}
