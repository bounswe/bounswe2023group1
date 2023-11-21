package com.groupa1.resq.controller;

import com.groupa1.resq.request.CreateActionRequest;
import com.groupa1.resq.request.CreateFeedbackRequest;
import com.groupa1.resq.request.CreateTaskRequest;
import com.groupa1.resq.response.TaskResponse;
import com.groupa1.resq.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

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

    @PreAuthorize("hasRole('RESPONDER')")
    @PostMapping("/declineTask")
    public ResponseEntity<String> declineTask(@RequestParam Long taskId, @RequestParam Long userId) {
        return taskService.declineTask(taskId, userId);
    }



    @PreAuthorize("hasRole('RESPONDER') or hasRole('COORDINATOR')")
    @GetMapping("/viewTasks")
    public ResponseEntity<List<TaskResponse>> viewAllTasks(@RequestParam Long userId) {
        return taskService.viewAllTasks(userId);
    }

    @PreAuthorize("hasRole('COORDINATOR')")
    @PostMapping("/deleteTask")
    public ResponseEntity<String> deleteTask(@RequestParam Long taskId) {
        return taskService.deleteTask(taskId);
    }

    @PreAuthorize("hasRole('COORDINATOR')")
    @PatchMapping("/updateTask")
    public ResponseEntity<String> updateTask(@RequestBody
                                             Map<Object, Object> fields, @RequestParam Long taskId)
            throws InvocationTargetException, IllegalAccessException {
        return taskService.updateTask(fields, taskId);
    }

    @PreAuthorize("hasRole('COORDINATOR')")
    @PostMapping("/assignTask")
    public ResponseEntity<String> assignTask(@RequestParam Long taskId, @RequestParam Long userId) {
        return taskService.assignTask(taskId, userId);
    }

    @PreAuthorize("hasRole('COORDINATOR')")
    @PostMapping("/unassignTask")
    public ResponseEntity<String> unassignTask(@RequestParam Long taskId, @RequestParam Long userId) {
        return taskService.unassignTask(taskId);
    }


    @PreAuthorize("hasRole('RESPPONDER')")
    @PostMapping("/completeTask")
    public ResponseEntity<String> completeTask(@RequestParam Long taskId, @RequestParam Long userId) {
        return taskService.completeTask(taskId, userId);
    }

    @PreAuthorize("hasRole('RESPONDER')")
    @PostMapping("/giveFeedback")
    public ResponseEntity<String> giveFeedback(CreateFeedbackRequest feedback) {
        return taskService.giveFeedback(feedback);
    }







}
