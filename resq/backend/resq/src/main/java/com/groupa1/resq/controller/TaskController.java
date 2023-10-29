package com.groupa1.resq.controller;

import com.groupa1.resq.request.CreateTaskRequest;
import com.groupa1.resq.response.TaskResponse;
import com.groupa1.resq.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PreAuthorize("hasRole('COORDINATOR')")
    @PostMapping("/createTask")
    public ResponseEntity<String> createTask(@RequestBody CreateTaskRequest createTaskRequest) {
        return taskService.createTask(createTaskRequest);
    }


    @PreAuthorize("hasRole('RESPONDER')")
    @PostMapping("/acceptTask")
    public ResponseEntity<String> acceptTask(@RequestParam Long taskId, @RequestParam Long userId) {
        return taskService.acceptTask(taskId, userId);
    }


    @PreAuthorize("hasRole('RESPONDER') or hasRole('COORDINATOR')")
    @GetMapping("/viewTasks")
    public ResponseEntity<List<TaskResponse>> viewAllTasks(@RequestParam Long userId) {
        return taskService.viewAllTasks(userId);
    }







}
