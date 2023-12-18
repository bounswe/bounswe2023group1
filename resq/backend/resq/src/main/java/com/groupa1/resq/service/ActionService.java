package com.groupa1.resq.service;


import com.groupa1.resq.converter.ActionConverter;
import com.groupa1.resq.entity.Action;
import com.groupa1.resq.entity.Comment;
import com.groupa1.resq.entity.Task;
import com.groupa1.resq.entity.User;
import com.groupa1.resq.entity.enums.ENotificationEntityType;
import com.groupa1.resq.exception.EntityNotFoundException;
import com.groupa1.resq.exception.NotOwnerException;
import com.groupa1.resq.repository.ActionRepository;
import com.groupa1.resq.repository.TaskRepository;
import com.groupa1.resq.repository.UserRepository;
import com.groupa1.resq.request.CreateCommentRequest;
import com.groupa1.resq.request.CreateActionRequest;
import com.groupa1.resq.dto.ActionDto;
import com.groupa1.resq.specification.ActionSpecifications;
import com.groupa1.resq.util.NotificationMessages;
import jakarta.transaction.Transactional;
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

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ActionConverter actionConverter;




    public ResponseEntity<Object> createAction(CreateActionRequest createActionRequest) {
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
        actionEntity.setTask(task);
        return ResponseEntity.ok(actionRepository.save(actionEntity).getId());


    }
    @Transactional
    public ResponseEntity<String> deleteAction(Long actionId){
        Action action = actionRepository.findById(actionId).orElse(null);
        if (action == null){
            log.error("No action found with id: {}", actionId);
            throw new EntityNotFoundException("No action found");
        }
        actionRepository.delete(action);
        return ResponseEntity.ok("Action deleted successfully");
    }

    @Transactional
    public ResponseEntity<String> updateAction(CreateActionRequest createActionRequest, Long actionId){
        Action action = actionRepository.findById(actionId).orElseThrow(()-> new EntityNotFoundException("No action found"));
        User verifier = userRepository.findById(createActionRequest.getVerifierId()).orElseThrow(()-> new EntityNotFoundException("No user found"));
        action.setVerifier(verifier);
        action.setDescription(createActionRequest.getDescription());
        action.setCompleted(createActionRequest.isCompleted());
        action.setVerified(createActionRequest.isVerified());
        action.setStartLatitude(createActionRequest.getStartLatitude());
        action.setStartLongitude(createActionRequest.getStartLongitude());
        action.setEndLatitude(createActionRequest.getEndLatitude());
        action.setEndLongitude(createActionRequest.getEndLongitude());
        action.setDueDate(createActionRequest.getDueDate());

        actionRepository.save(action);
        return ResponseEntity.ok("Action updated successfully");

    }

    public ResponseEntity<List<ActionDto>> viewActions(Long taskId){
        Optional<Task> task = taskRepository.findById(taskId);
        List<ActionDto> actionResponses = new ArrayList<>();
        if (task.isPresent()) {
            Set<Action> actions = task.get().getActions();
            actions.forEach(action -> {
                ActionDto actionResponse = new ActionDto();
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

    @Transactional
    public ResponseEntity<String> completeAction(Long actionId, Long userId){
        Action action = actionRepository.findById(actionId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        if (action == null){
            log.error("No action found with id: {}", actionId);
            throw new EntityNotFoundException("No action found");
        }
        if (user == null){
            log.error("No user found with id: {}", userId);
            throw new EntityNotFoundException("No user found");
        }
        if (action.getTask().getAssignee().getId() != userId) {
            log.error(
                    "User with id: {} is not the assignee for action with id: {}",
                    userId, actionId);
            throw new NotOwnerException("User is not the assignee for action");
        }
        action.setCompleted(true);
        actionRepository.save(action);

        String bodyMessage = String.format(NotificationMessages.ACTION_WAITING_FOR_VERIFICATION, action.getId());
        notificationService.sendNotification("Request Created", bodyMessage, action.getVerifier().getId(), action.getId() , ENotificationEntityType.RESOURCE);


        return ResponseEntity.ok("Action completed by responder");



    }

    @Transactional
    public ResponseEntity<String> verifyAction(Long actionId, Long userId) {
        Action action = actionRepository.findById(actionId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (action == null) {
            log.error("No action found with id: {}", actionId);
            throw new EntityNotFoundException("No action found");
        }
        if (user == null) {
            log.error("No user found with id: {}", userId);
            throw new EntityNotFoundException("No user found");
        }
        if (action.getVerifier() == null) {
            log.error("No verifier found for action with id: {}", actionId);
            throw new EntityNotFoundException("No verifier found for action");
        }
        if (action.getVerifier().getId() != userId) {
            log.error(
                    "User with id: {} is not the verifier for action with id: {}",
                    userId, actionId);
            throw new NotOwnerException("User is not the verifier for action");
        }
        action.setVerified(true);
        actionRepository.save(action);
        String bodyMessage = String.format(NotificationMessages.ACTION_VERIFIED, action.getId(), action.getVerifier().getId());
        notificationService.sendNotification("New Task Assigned", bodyMessage, user.getId(), action.getId(), ENotificationEntityType.TASK);
        return ResponseEntity.ok("Action verified by facilitator");
    }


    public ResponseEntity<String> commentAction(
            CreateCommentRequest commentActionRequest) {
    User user = userRepository.findById(commentActionRequest.getUserId()).orElse(null);
    Action action = actionRepository.findById(commentActionRequest.getActionId()).orElse(null);
    User verifier = action.getVerifier();
    if(action == null || user == null || verifier == null){
        log.error("Action, user, or action verifier is not found");
        throw new EntityNotFoundException("No action found");
    }
    if (user.getId() != verifier.getId()){
        log.error("User with id: {} is not the verifier for action with id: {}",
                user.getId(), action.getId());
        throw new NotOwnerException("User is not the verifier for action");
    }
    Comment comment = new Comment();
    comment.setVerifier(user);
    comment.setAction(action);
    comment.setComment(commentActionRequest.getComment());
    action.getComments().add(comment);
    return ResponseEntity.ok("Comment added successfully");

    }

    public ResponseEntity<List<ActionDto>> viewActionsByFilter(Long verifierId, Boolean isCompleted, LocalDateTime latestDueDate, LocalDateTime earliestDueDate) {
        Specification<Action> spec = Specification.where(null);

        if (verifierId != null) {
            spec = spec.and(ActionSpecifications.hasVerifier(verifierId));
        }

        if (isCompleted != null) {
            spec = spec.and(ActionSpecifications.hasCompleted(isCompleted));
        }

        if (latestDueDate != null) {
            spec = spec.and(ActionSpecifications.hasLatestDueDate(latestDueDate));
        }

        if (earliestDueDate != null) {
            spec = spec.and(ActionSpecifications.hasEarliestDueDate(earliestDueDate));
        }
        return ResponseEntity.ok(actionRepository.findAll(spec).stream().map(action -> actionConverter.convertToDto(action)).toList());


    }

}
