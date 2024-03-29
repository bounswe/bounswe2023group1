package com.groupa1.resq.controller;

import com.groupa1.resq.auth.UserDetailsImpl;
import com.groupa1.resq.entity.enums.EStatus;
import com.groupa1.resq.entity.enums.EUrgency;
import com.groupa1.resq.request.AddReqToTaskRequest;
import com.groupa1.resq.request.AddResourceToTaskRequest;
import com.groupa1.resq.request.CreateTaskRequest;
import com.groupa1.resq.dto.TaskDto;
import com.groupa1.resq.request.UpdateTaskRequest;
import com.groupa1.resq.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
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
    @GetMapping("/viewSingleTask")
    public ResponseEntity<TaskDto> viewSingleTask(@RequestParam Long taskId) {
        return taskService.viewSingleTask(taskId);
    }



    @PreAuthorize("hasRole('RESPONDER') or hasRole('COORDINATOR') or hasRole('FACILITATOR')")
    @GetMapping("/viewTasks")
    public ResponseEntity<List<TaskDto>> viewTasks(@RequestParam Long userId) {
        return taskService.viewTasks(userId);
    }

    @PreAuthorize("hasRole('COORDINATOR')")
    @PostMapping("/deleteTask")
    public ResponseEntity<String> deleteTask(@RequestParam Long taskId) {
        return taskService.deleteTask(taskId);
    }

    @PreAuthorize("hasRole('COORDINATOR') or hasRole('FACILITATOR')")
    @PostMapping("/updateTask")
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
    public ResponseEntity<String> addResources(@RequestBody AddResourceToTaskRequest addResourceToTaskRequest, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();
        log.info("Adding resources to task by coordinator with id: {}", userId);
        return taskService.addResources(addResourceToTaskRequest);
    }


    @PreAuthorize("hasRole('COORDINATOR')")
    @PostMapping("/removeResources")
    public ResponseEntity<String> removeResources(@RequestBody AddResourceToTaskRequest addResourceToTaskRequest, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();
        log.info("Adding resources to task by coordinator with id: {}", userId);
        return taskService.removeResources(addResourceToTaskRequest);
    }

    @PreAuthorize("hasRole('COORDINATOR') or hasRole('FACILITATOR') ")
    @PostMapping("/viewTaskByFilter")
    public ResponseEntity<List<TaskDto>> viewTaskByFilter(@RequestParam(required = false) Long assignerId, @RequestParam(required = false) Long assigneeId,
                                                               @RequestParam(required = false) EUrgency urgency, @RequestParam(required = false) EStatus status) {
        return taskService.viewTasksByFilter(assignerId, assigneeId, urgency, status);
    }

    @PreAuthorize("hasRole('COORDINATOR')")
    @PostMapping("/addRequestToTask")
    public ResponseEntity<String> addRequestToTask(@RequestBody
                                                   AddReqToTaskRequest addReqToTaskRequest, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();
        log.info("Adding resources to task by coordinator with id: {}", userId);
        return taskService.addRequestToTask(addReqToTaskRequest);
    }

    @PreAuthorize("hasRole('COORDINATOR')")
    @PostMapping("/removeRequestFromTask")
    public ResponseEntity<String> removeRequestFromTask(@RequestBody
                                                   AddReqToTaskRequest removeReqFromTask, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();
        log.info("Adding resources to task by coordinator with id: {}", userId);
        return taskService.removeRequestFromTask(removeReqFromTask);
    }
















}
