package com.groupa1.resq.controller;

import com.groupa1.resq.request.CreateCommentRequest;
import com.groupa1.resq.request.CreateActionRequest;
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
    @PostMapping("/deleteAction")
    public ResponseEntity<String> deleteAction(@RequestParam Long actionId) {
        return actionService.deleteAction(actionId);
    }

    @PreAuthorize("hasRole('COORDINATOR')")
    @PostMapping("/updateAction")
    public ResponseEntity<String> updateAction(@RequestBody CreateActionRequest createActionRequest, @RequestParam Long actionId) {
        return actionService.updateAction(createActionRequest, actionId);
    }



    @PreAuthorize("hasRole('RESPONDER')")
    @PostMapping("/completeAction")
    public ResponseEntity<String> completeAction(@RequestParam Long actionId, @RequestParam Long userId) {
        return actionService.completeAction(actionId, userId);
    }
    @PreAuthorize("hasRole('FACILITATOR')")
    @PostMapping("/verifyAction")
    public ResponseEntity<String> verifyAction(@RequestParam Long actionId, @RequestParam Long userId){
        return actionService.verifyAction(actionId, userId);
    }

    @PreAuthorize("hasRole('FACILITATOR')")
    @PostMapping("/commentAction")
    public ResponseEntity<String> verifyAction(@RequestBody
                                               CreateCommentRequest commentActionRequest){
        return actionService.commentAction(commentActionRequest);
    }






}
