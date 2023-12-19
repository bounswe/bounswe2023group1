package com.groupa1.resq.service;

import com.groupa1.resq.entity.Action;
import com.groupa1.resq.entity.Resource;
import com.groupa1.resq.entity.Task;
import com.groupa1.resq.entity.User;
import com.groupa1.resq.entity.enums.EGender;
import com.groupa1.resq.entity.enums.ENotificationEntityType;
import com.groupa1.resq.entity.enums.EStatus;
import com.groupa1.resq.entity.enums.EUrgency;
import com.groupa1.resq.repository.ActionRepository;
import com.groupa1.resq.repository.TaskRepository;
import com.groupa1.resq.repository.UserRepository;
import com.groupa1.resq.request.CreateTaskRequest;
import com.groupa1.resq.request.UpdateTaskRequest;
import com.groupa1.resq.response.ActionResponse;
import com.groupa1.resq.response.FeedbackResponse;
import com.groupa1.resq.response.ResourceResponse;
import com.groupa1.resq.response.TaskResponse;
import com.groupa1.resq.specification.TaskSpecifications;
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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j

public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActionRepository actionRepository;

    @Autowired
    NotificationService notificationService;

    @Transactional
    public ResponseEntity<String> createTask(CreateTaskRequest createTaskRequest) {
        if (createTaskRequest.getAssigneeId() == null || createTaskRequest.getAssignerId() == null) {
            return ResponseEntity.badRequest().body("Assignee and assigner must be specified");
        }
        User assignee = userService.findById(createTaskRequest.getAssigneeId());
        User assigner = userService.findById(createTaskRequest.getAssignerId());
        List<Action> actionEntities = new ArrayList<>();
        List<Resource> resourceEntities = new ArrayList<>();
        EUrgency urgency = createTaskRequest.getUrgency();
        EStatus status = EStatus.PENDING; // thought the default should be like pending
        String description = createTaskRequest.getDescription();


        createTaskRequest.getActions().forEach(action -> {
            Action actionEntity = new Action();
            User verifier = userService.findById(action.getVerifierId());
            String actionDescription = action.getDescription();
            BigDecimal startLatitude = action.getStartLatitude();
            BigDecimal startLongitude = action.getStartLongitude();
            BigDecimal endLatitude = action.getEndLatitude();
            BigDecimal endLongitude = action.getEndLongitude();
            actionEntity.setVerifier(verifier);
            actionEntity.setDescription(actionDescription);
            actionEntity.setCompleted(false);
            actionEntity.setStartLatitude(startLatitude);
            actionEntity.setStartLongitude(startLongitude);
            actionEntity.setEndLatitude(endLatitude);
            actionEntity.setEndLongitude(endLongitude);
            actionEntity.setCreatedAt(LocalDateTime.now());
            actionEntities.add(actionEntity);

        });

        createTaskRequest.getResources().forEach( resource -> {
            Resource resourceEntity = new Resource();
            User owner = userService.findById(resource.getSenderId());
            String categoryTreeId = resource.getCategoryTreeId();// I think this should a different calculation
            EGender gender = resource.getGender();
            Integer quantity = resource.getQuantity();
            BigDecimal latitude = resource.getLatitude();
            BigDecimal longitude = resource.getLongitude();
            resourceEntity.setSender(owner);
            resourceEntity.setCategoryTreeId(categoryTreeId);
            resourceEntity.setQuantity(quantity);
            resourceEntity.setGender(gender);
            resourceEntity.setLatitude(latitude);
            resourceEntity.setLongitude(longitude);
            resourceEntity.setCreatedAt(LocalDateTime.now());
            resourceEntities.add(resourceEntity);
        }
        );

        Task task = new Task();
        task.setAssignee(assignee);
        task.setAssigner(assigner);
        task.setStatus(status);
        task.setUrgency(urgency);
        task.setDescription(description);
        task.setActions(new HashSet<>(actionEntities));
        task.setResources(new HashSet<>(resourceEntities));
        task.setCreatedAt(LocalDateTime.now());

        Task savedTask = taskRepository.save(task);
        actionEntities.forEach(action -> {
            action.setTask(savedTask);
        });
        actionRepository.saveAll(actionEntities);

        String bodyMessage = String.format(NotificationMessages.TASK_ASSIGNED, assigner.getId(), task.getId());
        notificationService.sendNotification("New Task Assigned", bodyMessage, assignee.getId(), task.getId(), ENotificationEntityType.TASK);

        return ResponseEntity.ok("Task saved successfully");
    }



    public ResponseEntity<String> acceptTask(Long taskId, Long userId) {
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isPresent()) {
            Optional<User> user = userRepository.findById(userId);
            if (user.isPresent()){
                task.get().setAssignee(user.get());
                task.get().setStatus(EStatus.TODO);
                taskRepository.save(task.get());
                return ResponseEntity.ok("Task accepted");
            } else {
                log.error("No user found with id: {}", userId);
                return ResponseEntity.badRequest().body("User not found");
            }

        } else {
            log.error("No task found with id: {}", taskId);
            return ResponseEntity.badRequest().body("Task not found");
        }
    }

    public ResponseEntity<List<TaskResponse>> viewAllTasks(Long userId) {
        Optional<List<Task>> tasks = taskRepository.findByAssignee(userId);
        List<TaskResponse> taskResponses = new ArrayList<>();
        if (tasks.isPresent()) {
            for (Task task : tasks.get()) {
                TaskResponse taskResponse = new TaskResponse();
                taskResponse.setId(task.getId())
                        .setAssignee(task.getAssignee().getId())
                        .setAssigner(task.getAssigner().getId());
                Set<Action> actions = task.getActions();
                Set<ActionResponse> taskActions = new HashSet<>();
                actions.forEach( action -> {
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
                    taskActions.add(actionResponse);
                });
                taskResponse.setActions(taskActions);
                taskResponse.setDescription(task.getDescription());
                taskResponse.setUrgency(task.getUrgency());
                taskResponse.setStatus(task.getStatus());

                Set<ResourceResponse> taskResources = new HashSet<>();
                ResourceResponse resourceResponse = new ResourceResponse();
                task.getResources().forEach(resource -> {

                resourceResponse.setId(resource.getId())
                        .setSenderId(resource.getSender().getId()) // may change to resource.getOwner().getId()
                        .setQuantity(resource.getQuantity())
                        .setGender(resource.getGender())
                        .setCategoryId(resource.getCategoryTreeId())
                                .setLatitude(resource.getLatitude())
                                .setLongitude(resource.getLongitude());
                taskResources.add(resourceResponse);
                });
                Set<FeedbackResponse> taskFeedbacks = new HashSet<>();
                task.getFeedbacks().forEach(feedback -> {
                    FeedbackResponse feedbackResponse = new FeedbackResponse();
                    feedbackResponse.setId(feedback.getId())
                            .setTaskId(feedback.getTask().getId())
                            .setUserId(feedback.getCreator().getId())
                            .setMessage(feedback.getMessage());
                    taskFeedbacks.add(feedbackResponse);
                });
                taskResponse.setFeedbacks(taskFeedbacks);
                taskResponse.setResources(taskResources);
                taskResponses.add(taskResponse);


                }
            return ResponseEntity.ok(taskResponses);
        } else {
            log.error("No tasks found for userId: {}",  userId);
            return ResponseEntity.notFound().build();
        }

    }

    public ResponseEntity<String> updateTask(Long taskId, UpdateTaskRequest updateTaskRequest) {
        if (updateTaskRequest.getAssigneeId() == null || updateTaskRequest.getAssignerId() == null) {
            return ResponseEntity.badRequest().body("Assignee and assigner must be specified");
        }
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isPresent()) {
            User assignee = userService.findById(updateTaskRequest.getAssigneeId());
            User assigner = userService.findById(updateTaskRequest.getAssignerId());
            List<Action> actionEntities = new ArrayList<>();
            List<Resource> resourceEntities = new ArrayList<>();
            EUrgency urgency = updateTaskRequest.getUrgency();
            EStatus status = updateTaskRequest.getStatus();
            String description = updateTaskRequest.getDescription();
            updateTaskRequest.getActions().forEach(action -> {
                Action actionEntity = new Action();
                actionEntity.setVerifier(userService.findById(action.getVerifierId()));
                actionEntity.setDescription(action.getDescription());
                actionEntity.setCompleted(action.isCompleted());
                actionEntity.setVerified(action.isVerified());
                actionEntity.setStartLatitude(action.getStartLatitude());
                actionEntity.setStartLongitude(action.getStartLongitude());
                actionEntity.setEndLatitude(action.getEndLatitude());
                actionEntity.setEndLongitude(action.getEndLongitude());
                actionEntity.setCreatedAt(LocalDateTime.now());
                actionEntities.add(actionEntity);
                });
            updateTaskRequest.getResources().forEach(resource -> {
                Resource resourceEntity = new Resource();
                resourceEntity.setSender(userService.findById(resource.getSenderId()));
                resourceEntity.setCategoryTreeId(resource.getCategoryTreeId());
                resourceEntity.setQuantity(resource.getQuantity());
                resourceEntity.setGender(resource.getGender());
                resourceEntity.setLatitude(resource.getLatitude());
                resourceEntity.setLongitude(resource.getLongitude());
                resourceEntity.setCreatedAt(LocalDateTime.now());
                resourceEntities.add(resourceEntity);
            });
            Task taskEntity = task.get();
            taskEntity.setAssignee(assignee);
            taskEntity.setAssigner(assigner);
            taskEntity.setStatus(status);
            taskEntity.setUrgency(urgency);
            taskEntity.setDescription(description);
            taskEntity.setActions(new HashSet<>(actionEntities));
            taskEntity.setResources(new HashSet<>(resourceEntities));
            taskEntity.setCreatedAt(LocalDateTime.now());

            Task savedTask = taskRepository.save(taskEntity);
            actionEntities.forEach(action -> {
                action.setTask(savedTask);
            });
            actionRepository.saveAll(actionEntities);

            String bodyMessage = String.format(NotificationMessages.TASK_UPDATED, assigner.getId(), savedTask.getId());
            notificationService.sendNotification("New Task Assigned", bodyMessage, assignee.getId(), savedTask.getId(), ENotificationEntityType.TASK);

            return ResponseEntity.ok("Task updated successfully");

        }else{
            log.error("No task found with id: {}", taskId);
            return ResponseEntity.badRequest().body("Task not found");
        }
    }

    public ResponseEntity<List<TaskResponse>> viewTaskByFilter(Long assignerId, Long assigneeId, EUrgency urgency, EStatus status){

        Specification<Task> spec = Specification.where(null);

        if (assignerId != null) {
            spec = spec.and(TaskSpecifications.hasAssignerId(assignerId));
        }
        if (assigneeId != null) {
            spec = spec.and(TaskSpecifications.hasAssigneeId(assigneeId));
        }
        if (urgency != null) {
            spec = spec.and(TaskSpecifications.hasUrgency(urgency));
        }
        if (status != null) {
            spec = spec.and(TaskSpecifications.hasStatus(status));
        }

        List<TaskResponse> taskResponses = new ArrayList<>();
        List<Task> tasks = taskRepository.findAll(spec);

        tasks.forEach(task -> {
            TaskResponse taskResponse = new TaskResponse();
            taskResponse.setId(task.getId())
                    .setAssignee(task.getAssignee().getId())
                    .setAssigner(task.getAssigner().getId())
                    .setDescription(task.getDescription())
                    .setUrgency(task.getUrgency())
                    .setStatus(task.getStatus());
            Set<Action> actions = task.getActions();
            Set<ActionResponse> taskActions = new HashSet<>();
            actions.forEach( action -> {
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
                taskActions.add(actionResponse);
            });
            taskResponse.setActions(taskActions);
            Set<Resource> resources = task.getResources();
            Set<ResourceResponse> taskResources = new HashSet<>();
            resources.forEach( resource -> {
                ResourceResponse resourceResponse = new ResourceResponse();
                resourceResponse.setId(resource.getId())
                        .setSenderId(resource.getSender().getId())
                        .setQuantity(resource.getQuantity())
                        .setCategoryId(resource.getCategoryTreeId())
                        .setGender(resource.getGender())
                        .setLatitude(resource.getLatitude())
                        .setLongitude(resource.getLongitude());
            });
            taskResponse.setResources(taskResources);
            Set<FeedbackResponse> taskFeedbacks = new HashSet<>();
            task.getFeedbacks().forEach(feedback -> {
                FeedbackResponse feedbackResponse = new FeedbackResponse();
                feedbackResponse.setId(feedback.getId())
                        .setTaskId(feedback.getTask().getId())
                        .setUserId(feedback.getCreator().getId())
                        .setMessage(feedback.getMessage());
                taskFeedbacks.add(feedbackResponse);
            });
            taskResponse.setFeedbacks(taskFeedbacks);
            taskResponses.add(taskResponse);
        });

        return ResponseEntity.ok(taskResponses);


    }




}
