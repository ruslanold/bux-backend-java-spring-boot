package com.project.myproject.dao;

import com.project.myproject.dto.TaskReportStatusDto;
import com.project.myproject.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query( value = "select new com.project.myproject.dto.TaskReportStatusDto(r.status.name, count(r.status.id)) from Task t join t.reports r where t.id = :id GROUP BY r.status")
    List<TaskReportStatusDto> countReportsById(long id);

    @Query( value = "select t from Task t join t.category c where c.name like :categoryName and not exists (select th from t.hidden th where th.id = :userId)")
    List<Task> findAllByCategoryNameAndUserId(String categoryName, long userId);

}
