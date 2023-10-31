package com.groupa1.resq.controller;

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


}
