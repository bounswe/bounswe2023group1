package com.groupa1.resq.service;

import com.groupa1.resq.entity.Action;
import com.groupa1.resq.entity.Task;
import com.groupa1.resq.repository.ActionRepository;
import com.groupa1.resq.repository.TaskRepository;
import com.groupa1.resq.response.ActionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class ActionService {

    @Autowired
    private ActionRepository actionRepository;

    @Autowired
    private TaskRepository taskRepository;


    @PreAuthorize("hasRole('RESPONDER') or hasRole('COORDINATOR')")
    public ResponseEntity<List<ActionResponse>> viewActions(Long taskId){
        Optional<Task> task = taskRepository.findById(taskId);
        List<ActionResponse> actionResponses = new ArrayList<>();
        if (task.isPresent()) {
            Set<Action> actions = task.get().getActions();
            actions.forEach(action -> {
                ActionResponse actionResponse = new ActionResponse();
                actionResponse.setId(action.getId())
                        .setTaskId(action.getTask().getId())
                        .setVerifierId(action.getVerifier().getId())
                        .setDescription(action.getDescription())
                        .setCompleted(action.isCompleted())
                        .setStartLatitude(action.getStartLatitude())
                        .setStartLongitude(action.getStartLongitude())
                        .setEndLatitude(action.getEndLatitude())
                        .setEndLongitude(action.getEndLongitude())
                        .setDueDate(action.getDueDate());
                actionResponses.add(actionResponse);
            });
            return ResponseEntity.ok(actionResponses);
        } else {
            log.error("No task found with id: {}", taskId);
            return null;
        }
    }




}
