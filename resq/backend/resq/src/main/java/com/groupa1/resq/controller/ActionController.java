package com.groupa1.resq.controller;

import com.groupa1.resq.auth.UserDetailsImpl;
import com.groupa1.resq.request.CreateCommentRequest;
import com.groupa1.resq.request.CreateActionRequest;
import com.groupa1.resq.dto.ActionDto;
import com.groupa1.resq.request.UpdateActionRequest;
import com.groupa1.resq.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<Object> createAction(@RequestBody CreateActionRequest createActionRequest){
        return actionService.createAction(createActionRequest);
    }

    @PreAuthorize("hasRole('COORDINATOR') or hasRole('RESPONDER')")
    @GetMapping("/viewSingleAction")
    public ResponseEntity<ActionDto> viewSingleAction(@RequestParam Long actionId) {
        return actionService.viewSingleAction(actionId);
    }

    @PreAuthorize("hasRole('COORDINATOR')")
    @PostMapping("/deleteAction")
    public ResponseEntity<String> deleteAction(@RequestParam Long actionId) {
        return actionService.deleteAction(actionId);
    }

    @PreAuthorize("hasRole('COORDINATOR')")
    @PostMapping("/updateAction")
    public ResponseEntity<String> updateAction(@RequestBody
                                               UpdateActionRequest updateActionRequest, @RequestParam Long actionId) {
        return actionService.updateAction(updateActionRequest, actionId);
    }



    @PreAuthorize("hasRole('RESPONDER')")
    @PostMapping("/completeAction")
    public ResponseEntity<String> completeAction(@RequestParam Long actionId, Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();
        return actionService.completeAction(actionId, userId);
    }
    @PreAuthorize("hasRole('FACILITATOR')")
    @PostMapping("/verifyAction")
    public ResponseEntity<String> verifyAction(@RequestParam Long actionId, Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();
        return actionService.verifyAction(actionId, userId);
    }

    @PreAuthorize("hasRole('FACILITATOR')")
    @PostMapping("/commentAction")
    public ResponseEntity<String> verifyAction(@RequestBody
                                               CreateCommentRequest commentActionRequest){
        return actionService.commentAction(commentActionRequest);
    }

    @PreAuthorize("hasRole('COORDINATOR') or hasRole('FACILITATOR')")
    @GetMapping("/filterAction")
    public ResponseEntity<List<ActionDto>> viewActionsByFilter(@RequestParam(required = false) Long verifierId,
                                                               @RequestParam(required = false) Boolean isCompleted,
                                                               @RequestParam(required = false) LocalDateTime latestDueDate,
                                                               @RequestParam(required = false) LocalDateTime earliestDueDate,
                                                               @RequestParam(required = false) Long taskId,
                                                               @RequestParam(required = false) Boolean isVerified){

        return actionService.viewActionsByFilter(verifierId, isCompleted, latestDueDate, earliestDueDate, taskId, isVerified);
    }






}
