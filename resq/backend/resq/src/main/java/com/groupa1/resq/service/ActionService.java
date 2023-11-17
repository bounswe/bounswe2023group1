package com.groupa1.resq.service;

import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;
import com.groupa1.resq.entity.Action;
import com.groupa1.resq.entity.Task;
import com.groupa1.resq.entity.User;
import com.groupa1.resq.exception.EntityNotFoundException;
import com.groupa1.resq.repository.ActionRepository;
import com.groupa1.resq.repository.TaskRepository;
import com.groupa1.resq.repository.UserRepository;
import com.groupa1.resq.request.CreateActionRequest;
import com.groupa1.resq.response.ActionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    @Autowired
    private UserRepository userRepository;


    public ResponseEntity<String> createAction(CreateActionRequest createActionRequest) {
        if (!taskRepository.existsById(createActionRequest.getTaskId())){
            return ResponseEntity.badRequest().body("No task found");
        }

        // crating action within any task
        Action actionEntity = new Action();
        User verifier = userRepository.findById(createActionRequest.getVerifierId()).orElseThrow(()-> new EntityNotFoundException("No user found"));
        String description = createActionRequest.getDescription();
        LocalDateTime dueDate = createActionRequest.getDueDate();
        BigDecimal startLatitude = createActionRequest.getStartLatitude();
        BigDecimal startLongitude =createActionRequest.getStartLongitude();
        BigDecimal endLatitude = createActionRequest.getEndLatitude();
        BigDecimal endLongitude = createActionRequest.getEndLongitude();
        actionEntity.setVerifier(verifier);
        actionEntity.setCompleted(false); //default
        actionEntity.setVerified(false); // default
        actionEntity.setDescription(description);
        actionEntity.setDueDate(dueDate);
        actionEntity.setStartLatitude(startLatitude);
        actionEntity.setStartLongitude(startLongitude);
        actionEntity.setEndLatitude(endLatitude);
        actionEntity.setEndLongitude(endLongitude);
        actionEntity.setCreatedAt(LocalDateTime.now());

        Task task = taskRepository.findById(createActionRequest.getTaskId()).orElseThrow(()-> new EntityNotFoundException("No task found"));
        task.getActions().add(actionEntity);
        taskRepository.save(task);

        actionEntity.setTask(task);

        actionRepository.save(actionEntity);
        return ResponseEntity.ok("Action saved successfully!");

    }

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
