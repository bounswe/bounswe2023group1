package com.groupa1.resq.controller;

import com.groupa1.resq.auth.UserDetailsImpl;
import com.groupa1.resq.entity.enums.EStatus;
import com.groupa1.resq.request.AddResourceToTaskRequest;
import com.groupa1.resq.request.CreateTaskRequest;
import com.groupa1.resq.dto.TaskDto;
import com.groupa1.resq.request.UpdateTaskRequest;
import com.groupa1.resq.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    public ResponseEntity<Object> createTask(@RequestBody CreateTaskRequest createTaskRequest) {
        return taskService.createTask(createTaskRequest);
    }


    @PreAuthorize("hasRole('RESPONDER')")
    @PostMapping("/acceptTask")
    public ResponseEntity<String> acceptTask(@RequestParam Long taskId, Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();
        return taskService.acceptTask(taskId, userId);
    }

    @PreAuthorize("hasRole('RESPONDER')")
    @PostMapping("/declineTask")
    public ResponseEntity<String> declineTask(@RequestParam Long taskId, Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();
        return taskService.declineTask(taskId, userId);
    }



    @PreAuthorize("hasRole('RESPONDER') or hasRole('COORDINATOR')")
    @GetMapping("/viewTasks")
    public ResponseEntity<List<TaskDto>> viewTasks(@RequestParam Long userId) {
        return taskService.viewTasks(userId);
    }

    @PreAuthorize("hasRole('COORDINATOR')")
    @PostMapping("/deleteTask")
    public ResponseEntity<String> deleteTask(@RequestParam Long taskId) {
        return taskService.deleteTask(taskId);
    }

    @PreAuthorize("hasRole('COORDINATOR')")
    @PatchMapping("/updateTask")
    public ResponseEntity<String> updateTask(@RequestBody
                                                 UpdateTaskRequest updateTaskRequest, @RequestParam Long taskId){
        return taskService.updateTask(updateTaskRequest, taskId);
    }

    @PreAuthorize("hasRole('COORDINATOR')")
    @PostMapping("/assignTask")
    public ResponseEntity<String> assignTask(@RequestParam Long taskId, @RequestParam Long userId) {
        return taskService.assignTask(taskId, userId);
    }

    @PreAuthorize("hasRole('COORDINATOR')")
    @PostMapping("/unassignTask")
    public ResponseEntity<String> unassignTask(@RequestParam Long taskId) {
        return taskService.unassignTask(taskId);
    }


    @PreAuthorize("hasRole('RESPONDER')")
    @PostMapping("/completeTask")
    public ResponseEntity<String> completeTask(@RequestParam Long taskId, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();
        return taskService.completeTask(taskId, userId);
    }

    @PreAuthorize("hasRole('COORDINATOR')")
    @PostMapping("/addResources")
    public ResponseEntity<String> addResources(@RequestBody AddResourceToTaskRequest addResourceToTaskRequest) {
        return taskService.addResources(addResourceToTaskRequest);
    }


    @PreAuthorize("hasRole('COORDINATOR')")
    @PostMapping("/removeResources")
    public ResponseEntity<String> removeResources(AddResourceToTaskRequest addResourceToTaskRequest) {
        return taskService.removeResources(addResourceToTaskRequest);
    }


    @PreAuthorize("hasRole('COORDINATOR') or hasRole('RESPONDER')")
    @GetMapping("/viewTaskByFilter")
    public ResponseEntity<List<TaskDto>> viewTaskByFilter( @RequestParam(required = false) EStatus status, @RequestParam(required=false) Long assignerId,  @RequestParam(required=false) Long assigneeId) {
        return taskService.viewTasksByFilter(assignerId,assigneeId, status);
    }









}
