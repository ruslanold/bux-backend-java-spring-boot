package com.project.myproject.controller;

import com.project.myproject.dto.TaskCategoryDto;
import com.project.myproject.dto.TaskCreateDto;
import com.project.myproject.dto.TaskDto;
import com.project.myproject.entity.Task;
import com.project.myproject.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/tasks")
public class TaskController {

    @Autowired
    private ITaskService taskService;

    @GetMapping
    public List<TaskDto> getTasks(){
        return taskService.getTasks();
    }

    @GetMapping(value = "/category/{name}")
    public TaskCategoryDto getTasksByCategoryName(@PathVariable String name, Principal principal){
        return taskService.getTasksByCategoryNameAndUsername(name, principal.getName());
    }

    @GetMapping(value = "/{id}")
    public TaskDto getTaskById(@PathVariable long id){
        return taskService.getTaskById(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public TaskDto createTask(@RequestBody TaskCreateDto task){
        return taskService.createTask(task);
    }

    @PutMapping(value = "/{id}")
    public Task updateTask(@PathVariable long id, @RequestBody Task task){
        return taskService.updateTask(id, task);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable long id){
        taskService.deleteTask(id);
    }

}
