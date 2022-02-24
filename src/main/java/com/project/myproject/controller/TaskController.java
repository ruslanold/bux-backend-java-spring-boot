package com.project.myproject.controller;

import com.project.myproject.dto.*;
import com.project.myproject.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/tasks")
public class TaskController {

    @Autowired
    private ITaskService taskService;

    @GetMapping
    public List<TaskDto> getTasks(Principal principal){
        return taskService.getTasks(principal.getName());
    }

    @GetMapping(value = "/adv")
    public List<TaskUserDto> getUserTasks(Principal principal){
        return taskService.getUserTasks(principal.getName());
    }

    @GetMapping(value = "/category/{name}")
    public TaskCategoryDto getTasksByCategoryName(@PathVariable String name, Principal principal){
        return taskService.getTasksByCategoryNameAndUsername(name, principal.getName());
    }

    @GetMapping(value = "/{id}")
    public TaskUserReportsDto getTaskById(@PathVariable long id, Principal principal){
        return taskService.getTaskByIdWithUserReports(id, principal.getName());
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public TaskCreateUpdateDto createTask(@RequestBody @Valid TaskCreateUpdateDto task, Principal principal){
        return taskService.createTask(task, principal.getName());
    }

    @PutMapping(value = "/{id}")
    public TaskCreateUpdateDto updateTask(@PathVariable long id,@Valid @RequestBody TaskCreateUpdateDto task, Principal principal){
        return taskService.updateTask(id, task, principal.getName());
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable long id){
        taskService.deleteTask(id);
    }

}
