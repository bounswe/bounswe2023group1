package com.groupa1.resq.service;

import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;
import com.groupa1.resq.entity.Action;
import com.groupa1.resq.entity.Need;
import com.groupa1.resq.entity.Task;
import com.groupa1.resq.entity.User;
import com.groupa1.resq.exception.EntityNotFoundException;
import com.groupa1.resq.repository.ActionRepository;
import com.groupa1.resq.repository.TaskRepository;
import com.groupa1.resq.repository.UserRepository;
import com.groupa1.resq.request.CreateActionRequest;
import com.groupa1.resq.request.UpdateActionRequest;
import com.groupa1.resq.response.ActionResponse;
import com.groupa1.resq.specification.ActionSpecifications;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
                        .setDueDate(action.getDueDate())
                        .setCreatedDate(action.getCreatedAt());
                actionResponses.add(actionResponse);
            });
            return ResponseEntity.ok(actionResponses);
        } else {
            log.error("No task found with id: {}", taskId);
            return null;
        }
    }

    public ResponseEntity<String> updateAction(Long actionId, UpdateActionRequest updateActionRequest) {
        Optional<Action> action = actionRepository.findById(actionId);
        if (action.isPresent()) {
            Action actionEntity = action.get();
            User verifier = userRepository.findById(updateActionRequest.getVerifierId()).orElseThrow(()-> new EntityNotFoundException("No user found"));
            String description = updateActionRequest.getDescription();
            LocalDateTime dueDate = updateActionRequest.getDueDate();
            BigDecimal startLatitude = updateActionRequest.getStartLatitude();
            BigDecimal startLongitude =updateActionRequest.getStartLongitude();
            BigDecimal endLatitude = updateActionRequest.getEndLatitude();
            BigDecimal endLongitude = updateActionRequest.getEndLongitude();
            boolean isCompleted = updateActionRequest.isCompleted();
            boolean isVerified = updateActionRequest.isVerified();
            actionEntity.setVerifier(verifier);
            actionEntity.setCompleted(isCompleted);
            actionEntity.setVerified(isVerified);
            actionEntity.setDescription(description);
            actionEntity.setDueDate(dueDate);
            actionEntity.setStartLatitude(startLatitude);
            actionEntity.setStartLongitude(startLongitude);
            actionEntity.setEndLatitude(endLatitude);
            actionEntity.setEndLongitude(endLongitude);
            actionRepository.save(actionEntity);
            return ResponseEntity.ok("Action updated successfully!");
        } else {
            log.error("No action found with id: {}", actionId);
            return null;
        }
    }

    public ResponseEntity<String> deleteAction(Long actionId, Long taskId) {
        Optional<Action> action = actionRepository.findById(actionId);
        if (action.isPresent()) {
            Action actionEntity = action.get();
            Task task = taskRepository.findById(taskId).orElseThrow(()-> new EntityNotFoundException("No task found"));
            task.getActions().remove(actionEntity);
            taskRepository.save(task);
            actionRepository.deleteById(actionId);
            return ResponseEntity.ok("Action deleted successfully!");
        } else {
            log.error("No action found with id: {}", actionId);
            return null;
        }
    }

    public ResponseEntity<List<ActionResponse>> viewActionByFilter(Long taskId, Long verifierId, Boolean isCompleted, Boolean isVerified, LocalDateTime dueDate){

        Specification<Action> spec = Specification.where(null);

        if (taskId != null) {
            spec = spec.and(ActionSpecifications.hasTaskId(taskId));
        }
        if (verifierId != null) {
            spec = spec.and(ActionSpecifications.hasVerifierId(verifierId));
        }
        if (isCompleted != null) {
            spec = spec.and(ActionSpecifications.hasCompleted(isCompleted));
        }
        if (isVerified != null) {
            spec = spec.and(ActionSpecifications.hasVerified(isVerified));
        }
        if (dueDate != null) {
            spec = spec.and(ActionSpecifications.hasDueDate(dueDate));
        }

        List<ActionResponse> actionResponses = new ArrayList<>();
        List<Action> actions = actionRepository.findAll(spec);
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
                    .setDueDate(action.getDueDate())
                    .setCreatedDate(action.getCreatedAt());
            actionResponses.add(actionResponse);
        });

        return ResponseEntity.ok(actionResponses);
    }

    public ResponseEntity<String> verifyAction(Long actionId, Long verifierId) {
        Optional<Action> action = actionRepository.findById(actionId);
        if (action.isPresent()) {
            Action actionEntity = action.get();
            User verifier = userRepository.findById(verifierId).orElseThrow(() -> new EntityNotFoundException("No user found"));
            if (actionEntity.getVerifier().getId() != verifier.getId()) {
                return ResponseEntity.badRequest().body("User is not the verifier of the action");
            } else {
                actionEntity.setVerified(true);
                actionRepository.save(actionEntity);
                return ResponseEntity.ok("Action verified successfully!");
            }
        } else {
            log.error("No action found with id: {}", actionId);
            return null;
        }
    }






}
