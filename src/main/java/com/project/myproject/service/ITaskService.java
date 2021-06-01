package com.project.myproject.service;

import com.project.myproject.dto.TaskCategoryDto;
import com.project.myproject.dto.TaskCreateDto;
import com.project.myproject.dto.TaskDto;
import com.project.myproject.dto.TaskReportStatusDto;
import com.project.myproject.entity.Task;

import java.util.List;

public interface ITaskService {

    List<TaskDto> getTasks();
    TaskCategoryDto getTasksByCategoryNameAndUsername(String categoryName, String username);
    TaskDto getTaskById(long id);
    TaskDto createTask(TaskCreateDto task);
    Task updateTask(long id, Task task);
    void deleteTask(long id);

    List<TaskReportStatusDto> countReportsById(long id);

}
