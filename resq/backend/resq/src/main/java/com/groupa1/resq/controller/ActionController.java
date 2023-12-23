package com.groupa1.resq.controller;

import com.groupa1.resq.request.CreateActionRequest;
import com.groupa1.resq.request.UpdateActionRequest;
import com.groupa1.resq.response.ActionResponse;
import com.groupa1.resq.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/action")
public class ActionController {

    @Autowired
    private ActionService actionService;


    @PreAuthorize("hasRole('COORDINATOR')")
    @PostMapping("/createAction")
    public ResponseEntity<String> createAction(@RequestBody CreateActionRequest createActionRequest){
        return actionService.createAction(createActionRequest);
    }

    @PreAuthorize("hasRole('RESPONDER') or hasRole('COORDINATOR')")
    @GetMapping("/viewActions")
    public ResponseEntity<List<ActionResponse>> viewActions(@RequestParam Long taskId) {
        return actionService.viewActions( taskId);
    }

    @PreAuthorize("hasRole('COORDINATOR')")
    @GetMapping("/updateAction")
    public ResponseEntity<String> updateAction(@RequestBody UpdateActionRequest updateActionRequest, @RequestParam Long actionId){
        return actionService.updateAction(actionId, updateActionRequest);
    }

    @PreAuthorize("hasRole('COORDINATOR')")
    @GetMapping("/deleteAction")
    public ResponseEntity<String> deleteAction(@RequestParam Long actionId, @RequestParam Long taskId){
        return actionService.deleteAction(actionId, taskId);
    }

    @PreAuthorize("hasRole('COORDINATOR')")
    @GetMapping("/viewActionByFilter")
    public ResponseEntity<List<ActionResponse>> viewActionByFilter(@RequestParam(required = false) Long taskId,
                                                                   @RequestParam(required = false) Long verifierId,
                                                                   @RequestParam(required = false) Boolean isCompleted,
                                                                   @RequestParam(required = false) Boolean isVerified,
                                                                   @RequestParam(required = false) LocalDateTime dueDate){
        return actionService.viewActionByFilter(taskId, verifierId, isCompleted, isVerified, dueDate);
    }

    @PreAuthorize("hasRole('FACILITATOR')")
    @GetMapping("/verifyAction")
    public ResponseEntity<String> verifyAction(@RequestParam Long actionId, @RequestParam Long verifierId){
        return actionService.verifyAction(actionId, verifierId);
    }


}
