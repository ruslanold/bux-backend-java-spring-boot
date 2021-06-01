package com.project.myproject.service;

import com.project.myproject.dao.TaskRepository;
import com.project.myproject.dto.*;
import com.project.myproject.entity.Category;
import com.project.myproject.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService implements ITaskService{

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private IUserService userService;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IStorageService storageService;

    @Override
    public List<TaskDto> getTasks() {
        return taskRepository.findAll().stream().map(t -> convertToTaskDto(t)).collect(Collectors.toList());
    }

    @Override
    public TaskCategoryDto getTasksByCategoryNameAndUsername(String categoryName, String username) {
        Category category = categoryService.getCategoryWithTasksByNameAndUsername(categoryName, username);
        List<TaskDto> taskDtos = category.getTasks().stream().map(t -> convertToTaskDto(t)).collect(Collectors.toList());
        return new TaskCategoryDto(category.getId(), taskDtos);
    }

    @Override
    public TaskDto getTaskById(long id) {
        if (!taskRepository.existsById(id)){
            throw new IllegalArgumentException("No task with such id: " + id);
        }
        return convertToTaskDto(taskRepository.getOne(id));
    }

    @Override
    public TaskDto createTask(TaskCreateDto task) {

        Task taskEntity = new Task();
        taskEntity.setTitle(task.getTitle());
        taskEntity.setDescription(task.getDescription());
        taskEntity.setUser(userService.getUserById(task.getUserId()));
        taskEntity.setCategory(categoryService.getCategoryById(task.getCategoryId()));
        taskEntity.setPrice(task.getPrice());

        return convertToTaskDto(taskRepository.saveAndFlush(taskEntity));
    }

    @Override
    public Task updateTask(long id, Task task) {
        if (!taskRepository.existsById(id)){
            throw new IllegalArgumentException("No task with such id: " + id);
        }
        task.setId(id);
        return taskRepository.saveAndFlush(task);
    }

    @Override
    public void deleteTask(long id) {
        if (!taskRepository.existsById(id)){
            throw new IllegalArgumentException("No task with such id: " + id);
        }
        taskRepository.deleteById(id);
    }

    public List<TaskReportStatusDto> countReportsById(long id){
        return taskRepository.countReportsById(id);
    }

    public TaskDto convertToTaskDto(Task task) {
        String imageFullUrl = storageService.getUrlFromS3(task.getUser().getImage()).toString();
        List<TaskReportStatusDto> reportsStatus = countReportsById(task.getId());
        TaskUserDto user = new TaskUserDto(task.getUser().getId(), task.getUser().getUsername(), imageFullUrl);
        return new TaskDto(task.getId(), task.getTitle(), task.getDescription(), task.getPrice(), user, task.getCategory().getId(), reportsStatus);
    }

}
