package com.project.myproject.service;

import com.project.myproject.dto.*;
import com.project.myproject.entity.Task;

import java.util.List;

public interface ITaskService {

    List<TaskDto> getTasks(String username);
    List<TaskUserDto> getUserTasks(String username);
    TaskCategoryDto getTasksByCategoryNameAndUsername(String categoryName, String username);
    Task getTaskById(long id);
    TaskUserReportsDto getTaskByIdWithUserReports(long id, String username);
    List<TaskDto> getTasksByUserId(long userId);
    TaskCreateUpdateDto createTask(TaskCreateUpdateDto task, String username);
    TaskCreateUpdateDto updateTask(long id, TaskCreateUpdateDto task, String username);
    void updateBalance(Task task);
    void deleteTask(long id);
}
