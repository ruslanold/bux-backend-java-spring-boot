package com.project.myproject.dao;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.project.myproject.dto.TaskReportStatusDto;
import com.project.myproject.entity.Task;
import com.project.myproject.enums.ECountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query( value = "select new com.project.myproject.dto.TaskReportStatusDto(r.status.name, count(t.id)) from Task t join t.reports r where t.id = :id GROUP BY r.status.name")
    List<TaskReportStatusDto> countReportsById(long id);

    @Query( value = "select t from Task t join t.category cat join t.countries c where cat.name like :categoryName and (t.geoFilter = 1 and c.name = :country or t.geoFilter = 2 and not c.name = :country) and not exists (select th from t.hidden th where th.id = :userId)")
    List<Task> findAllByCategoryNameAndCountriesAndNotExistsUserId(String categoryName, ECountry country, long userId);

    @Query(value = "select t from Task t left join t.countries c where t.enabled = true and ( (t.geoFilter = 0 ) or (t.geoFilter = 1 and c.name = :country) or (t.geoFilter = 2 and not c.name = :country)) and not exists (select th from t.hidden th where th.username = :username)")
    List<Task> findAllByEnabledTrueAndGeoFilterAndNotExistsUsername(ECountry country, String username);

    @Query(value = "select t from Task t join fetch t.reports r where (t.id = :taskId and r.user.username like :username) or t.id = :taskId")
    Task findByIdWithUserReports(long taskId, String username);

    @Query(value = "select t from Task t where t.user.id = :userId")
    List<Task> findAllByUserId(long userId);

    @Query(value = "select t from Task t where t.user.username = :username")
    List<Task> findAllByUserUsername(String username);

    @Override
    List<Task> findAll();

    @Query(value = "select t from Task t where t.id = :id and t.showInDays & :day", nativeQuery = true)
    Task findByIdAndDaysOfWeek(long id, int day);
}
