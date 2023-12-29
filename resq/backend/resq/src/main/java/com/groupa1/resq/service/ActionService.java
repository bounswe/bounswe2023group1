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
import com.groupa1.resq.request.UpdateActionRequest;
import com.groupa1.resq.dto.ActionDto;
import com.groupa1.resq.specification.ActionSpecifications;
import com.groupa1.resq.util.NotificationMessages;
import jakarta.transaction.Transactional;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Collections;

@Service
@Slf4j
@Setter
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
        taskRepository.save(task);

        actionEntity.setTask(task);
        return ResponseEntity.ok(actionRepository.save(actionEntity).getId());


    }

public ResponseEntity<ActionDto> viewSingleAction(Long actionId){
        Action action = actionRepository.findById(actionId).orElse(null);
        if (action == null){
            log.error("No action found with id: {}", actionId);
            throw new EntityNotFoundException("No action found");
        }
        return ResponseEntity.ok(actionConverter.convertToDto(action));
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
    public ResponseEntity<String> updateAction(UpdateActionRequest updateActionRequest, Long actionId){
        Action action = actionRepository.findById(actionId).orElseThrow(()-> new EntityNotFoundException("No action found"));
        User verifier = userRepository.findById(updateActionRequest.getVerifierId()).orElseThrow(()-> new EntityNotFoundException("No user found"));
        action.setVerifier(verifier);
        action.setDescription(updateActionRequest.getDescription());
        action.setCompleted(updateActionRequest.isCompleted());
        action.setVerified(updateActionRequest.isVerified());
        action.setStartLatitude(updateActionRequest.getStartLatitude());
        action.setStartLongitude(updateActionRequest.getStartLongitude());
        action.setEndLatitude(updateActionRequest.getEndLatitude());
        action.setEndLongitude(updateActionRequest.getEndLongitude());
        action.setDueDate(updateActionRequest.getDueDate());

        actionRepository.save(action);
        return ResponseEntity.ok("Action updated successfully");

    }

    // acted one completes the action
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
        notificationService.sendNotification("Action Verified", bodyMessage, user.getId(), action.getId(), ENotificationEntityType.TASK);
        return ResponseEntity.ok("Action verified by facilitator");
    }


    public ResponseEntity<String> commentAction(CreateCommentRequest commentActionRequest) {
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
    Set<Comment> comments = new HashSet<>(action.getComments() != null ? action.getComments() : Collections.emptySet());
    comments.add(comment);
    action.setComments(comments);
    return ResponseEntity.ok("Comment added successfully");

    }

    public ResponseEntity<List<ActionDto>> viewActionsByFilter(Long verifierId, Boolean isCompleted, LocalDateTime latestDueDate, LocalDateTime earliestDueDate, Long taskId, Boolean isVerified){
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
        if (taskId != null) {
            spec = spec.and(ActionSpecifications.hasTaskId(taskId));
        }
        if (isVerified != null){
            spec = spec.and(ActionSpecifications.hasVerified(isVerified));
        }

        return ResponseEntity.ok(actionRepository.findAll(spec).stream().map(action -> actionConverter.convertToDto(action)).toList());


    }

}
