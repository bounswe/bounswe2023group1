package com.groupa1.resq.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupa1.resq.entity.Action;
import com.groupa1.resq.entity.Feedback;
import com.groupa1.resq.entity.Resource;
import com.groupa1.resq.entity.Task;
import com.groupa1.resq.entity.User;
import com.groupa1.resq.entity.enums.EGender;
import com.groupa1.resq.entity.enums.ENotificationEntityType;
import com.groupa1.resq.entity.enums.EStatus;
import com.groupa1.resq.entity.enums.EUrgency;
import com.groupa1.resq.exception.EntityNotFoundException;
import com.groupa1.resq.repository.ActionRepository;
import com.groupa1.resq.repository.TaskRepository;
import com.groupa1.resq.repository.UserRepository;
import com.groupa1.resq.request.CreateFeedbackRequest;
import com.groupa1.resq.request.CreateTaskRequest;
import com.groupa1.resq.response.ActionResponse;
import com.groupa1.resq.response.FeedbackResponse;
import com.groupa1.resq.response.ResourceResponse;
import com.groupa1.resq.response.TaskResponse;
import com.groupa1.resq.utils.NullAwareBeanUtilsBean;
import com.groupa1.resq.util.NotificationMessages;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
    private ObjectMapper objectMapper;

    @Autowired
    private NullAwareBeanUtilsBean beanUtils;

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
        EStatus status = EStatus.PENDING;
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

    public ResponseEntity<String> declineTask(Long taskId, Long userId){
        Task task = taskRepository.findById(taskId).orElseThrow(()-> new EntityNotFoundException("No task found"));
        User user = userRepository.findById(userId).orElseThrow(()-> new EntityNotFoundException("No user found"));
        if (task.getAssignee().getId() != user.getId()){
            log.error("User is not the assignee of the task");
            return ResponseEntity.badRequest().body("User is not the assignee of the task");
        }
        task.setStatus(EStatus.DECLINED);
        taskRepository.save(task);
        return ResponseEntity.ok("Task declined");
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
                        .setSenderId(resource.getSender().getId())
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
    @Transactional
    public ResponseEntity<String> deleteTask(@RequestParam Long taskId){
        Task task = taskRepository.findById(taskId).orElseThrow(()-> new EntityNotFoundException("No task found"));
        taskRepository.delete(task);
        return ResponseEntity.ok("Task deleted successfully");
    }

    @Transactional
    public ResponseEntity<String> updateTask(Map<Object, Object> fields, Long taskId)

            throws InvocationTargetException, IllegalAccessException {
        //TODO: implement other update methods to update specific fields
        Task task = taskRepository.findById(taskId).orElseThrow(()-> new EntityNotFoundException("No task found"));
        Task updatedTask = objectMapper.convertValue(fields, Task.class);
        beanUtils.copyProperties(task, updatedTask); // copy fields of updatedTask to task ignoring null values
        taskRepository.save(task);
        return ResponseEntity.ok("Task updated successfully");
    }
    public ResponseEntity<String> assignTask(Long taskId, Long userId){
        Task task = taskRepository.findById(taskId).orElseThrow(()-> new EntityNotFoundException("No task found"));
        User user = userRepository.findById(userId).orElseThrow(()-> new EntityNotFoundException("No user found"));
        if (task.getAssignee() != null){
            log.error("Task already assigned");
            return ResponseEntity.badRequest().body("Task already assigned");
        }
        task.setAssignee(user);
        task.setStatus(EStatus.PENDING);
        taskRepository.save(task);
        User assigner = task.getAssigner();
        String bodyMessage = String.format(NotificationMessages.TASK_ASSIGNED, assigner.getId(), task.getId());
        notificationService.sendNotification("New Task Assigned", bodyMessage, user.getId(), task.getId(), ENotificationEntityType.TASK);

        return ResponseEntity.ok("Task assigned successfully");
    }

    public ResponseEntity<String> unassignTask(Long taskId){
        Task task = taskRepository.findById(taskId).orElseThrow(()-> new EntityNotFoundException("No task found"));
        User user = userRepository.findById(task.getAssignee().getId()).orElseThrow(()-> new EntityNotFoundException("No user found"));

        task.setAssignee(null);
        task.setStatus(EStatus.FREE);
        taskRepository.save(task);
        User assigner = task.getAssigner();
        String bodyMessage = String.format(NotificationMessages.TASK_UNASSIGNED, assigner.getId(), task.getId());
        notificationService.sendNotification("You Are Unassigned", bodyMessage, user.getId(), task.getId(), ENotificationEntityType.TASK);
        return ResponseEntity.ok("Task unassigned successfully");
    }

    public ResponseEntity<String> completeTask(Long taskId, Long userId){
        Task task = taskRepository.findById(taskId).orElseThrow(()-> new EntityNotFoundException("No task found"));
        User user = userRepository.findById(userId).orElseThrow(()-> new EntityNotFoundException("No user found"));
        if (task.getAssignee().getId() != user.getId()){
            log.error("User is not the assignee of the task");
            return ResponseEntity.badRequest().body("User is not the assignee of the task");
        }
        if (task.getStatus() != EStatus.TODO){
            log.error("Task is not in progress");
            return ResponseEntity.badRequest().body("Task is not in progress");
        }
        Set<Action> actions = task.getActions();
        actions.stream().filter(action -> !action.isCompleted());
        if (actions.size() > 0){
            log.error("Task is not completed, there are actions not completed");
            return ResponseEntity.badRequest().body("Actions are not done");
        }

        task.setStatus(EStatus.DONE);
        taskRepository.save(task);

        return ResponseEntity.ok("Task completed");
    }

    public ResponseEntity<String> giveFeedback(CreateFeedbackRequest feedbackRequest){
        Task task = taskRepository.findById(feedbackRequest.getTaskId()).orElseThrow(()-> new EntityNotFoundException("No task found"));
        User user = userRepository.findById(feedbackRequest.getUserId()).orElseThrow(()-> new EntityNotFoundException("No user found"));
        Feedback feedback = new Feedback();
        feedback.setCreator(user);
        feedback.setMessage(feedbackRequest.getMessage());
        feedback.setTask(task);
        task.getFeedbacks().add(feedback);
        // TODO: send notification to coordinator
        taskRepository.save(task);
        return ResponseEntity.ok("Feedback saved successfully");
    }




}
