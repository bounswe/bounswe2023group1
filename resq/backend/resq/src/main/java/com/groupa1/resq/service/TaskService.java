package com.groupa1.resq.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupa1.resq.converter.ActionConverter;
import com.groupa1.resq.converter.TaskConverter;
import com.groupa1.resq.dto.ResourceDto;
import com.groupa1.resq.entity.Action;
import com.groupa1.resq.entity.Need;
import com.groupa1.resq.entity.Request;
import com.groupa1.resq.entity.Resource;
import com.groupa1.resq.entity.Task;
import com.groupa1.resq.entity.User;
import com.groupa1.resq.entity.enums.ENeedStatus;
import com.groupa1.resq.entity.enums.ENotificationEntityType;
import com.groupa1.resq.entity.enums.ERequestStatus;
import com.groupa1.resq.entity.enums.EResourceStatus;
import com.groupa1.resq.entity.enums.EStatus;
import com.groupa1.resq.entity.enums.EUrgency;
import com.groupa1.resq.exception.EntityNotFoundException;
import com.groupa1.resq.repository.ActionRepository;
import com.groupa1.resq.repository.NeedRepository;
import com.groupa1.resq.repository.RequestRepository;
import com.groupa1.resq.repository.ResourceRepository;
import com.groupa1.resq.repository.TaskRepository;
import com.groupa1.resq.repository.UserRepository;
import com.groupa1.resq.request.AddReqToTaskRequest;
import com.groupa1.resq.request.AddResourceToTaskRequest;
import com.groupa1.resq.request.CreateTaskRequest;
import com.groupa1.resq.dto.TaskDto;
import com.groupa1.resq.request.UpdateTaskRequest;
import com.groupa1.resq.specification.TaskSpecifications;
import com.groupa1.resq.util.NotificationMessages;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
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


    @Autowired
    private TaskConverter taskConverter;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private NeedRepository needRepository;

    @Transactional
    public ResponseEntity<Object> createTask(CreateTaskRequest createTaskRequest) {
        if (createTaskRequest.getAssigneeId() == null || createTaskRequest.getAssignerId() == null) {
            return ResponseEntity.badRequest().body("Assignee and assigner must be specified");
        }
        User assigner = userService.findById(createTaskRequest.getAssignerId());
        User assignee = userService.findById(createTaskRequest.getAssigneeId());

//        if (!assignee.getRoles().contains("RESPONDER")){
//            return ResponseEntity.badRequest().body("Assignee must be a responder");
//        }
        List<Action> actionEntities = new ArrayList<>();
        List<Resource> resourceEntities = new ArrayList<>();
        EUrgency urgency = createTaskRequest.getUrgency();
        EStatus status = EStatus.PENDING;
        String description = createTaskRequest.getDescription();


        Task task = new Task();
        task.setAssignee(assignee);
        task.setAssigner(assigner);
        task.setStatus(status);
        task.setUrgency(urgency);
        task.setDescription(description);
        task.setActions(new HashSet<>(actionEntities));
        task.setResources(new HashSet<>(resourceEntities));
        task.setCreatedAt(LocalDateTime.now());
        task.setModifiedAt(LocalDateTime.now());
        actionRepository.saveAll(actionEntities);
        Task savedTask = taskRepository.save(task);

        String bodyMessage = String.format(NotificationMessages.TASK_ASSIGNED, assigner.getId(), task.getId());
        notificationService.sendNotification("New Task Assigned", bodyMessage, assignee.getId(), task.getId(), ENotificationEntityType.TASK);

        return ResponseEntity.ok(savedTask.getId());
    }



    public ResponseEntity<String> acceptTask(Long taskId, Long userId) {
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isPresent()) {
            if (task.get().getAssignee().getId() != userId){
                log.error("User is not the assignee of the task");
                return ResponseEntity.badRequest().body("User is not the assignee of the task");
            }
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

    public ResponseEntity<TaskDto> viewSingleTask(Long taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isPresent()) {
            TaskDto taskResponse = taskConverter.convertToDto(task.get());
            return ResponseEntity.ok(taskResponse);
        } else {
            log.error("No task found with id: {}", taskId);
            return ResponseEntity.notFound().build();
        }
    }



    public ResponseEntity<List<TaskDto>> viewTasks(Long userId) {
        Optional<List<Task>> tasks = taskRepository.findByAssignee(userId);
        List<TaskDto> taskResponses = new ArrayList<>();
        if (tasks.isPresent()) {
            for (Task task : tasks.get()) {
                TaskDto taskResponse = taskConverter.convertToDto(task);
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
    public ResponseEntity<String> updateTask(UpdateTaskRequest updateTaskRequest, Long taskId) {

        Task task = taskRepository.findById(taskId).orElseThrow(()-> new EntityNotFoundException("No task found"));
        task.setDescription(updateTaskRequest.getDescription() != null ? updateTaskRequest.getDescription() :
                task.getDescription());
        task.setUrgency(updateTaskRequest.getUrgency() != null ? updateTaskRequest.getUrgency() : task.getUrgency());
        task.setStatus(updateTaskRequest.getStatus() != null ? updateTaskRequest.getStatus() : task.getStatus());
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

    @Transactional
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
        if (actions.stream().anyMatch(action -> !action.isCompleted())){
            log.error("Task is not completed, there are actions not completed");
            return ResponseEntity.badRequest().body("Actions are not done");
        }

        Set<Resource> resources = task.getResources();
        resources.forEach(
                resource -> {
                    resource.setStatus(EResourceStatus.DELIVERED);
                    resourceRepository.save(resource);
                }
        );

        Set<Request> requests = task.getRequests();
        requests.forEach(
                request -> {
                    request.setStatus(ERequestStatus.PROVIDED);
                    requestRepository.save(request);
                    Set<Need> needs = request.getNeeds();
                    needs.forEach(
                            need -> {
                                need.setStatus(ENeedStatus.PROVIDED);
                                needRepository.save(need);
                            }
                    );
                }
        );


        task.setStatus(EStatus.DONE);
        taskRepository.save(task);

        return ResponseEntity.ok("Task completed");
    }


    @Transactional
    public ResponseEntity<String> addResources(AddResourceToTaskRequest addResourceToTaskRequest){
        Long taskId = addResourceToTaskRequest.getTaskId();
        List<Long> resources = addResourceToTaskRequest.getResourceIds();
        Task task = taskRepository.findById(taskId).orElseThrow(()-> new EntityNotFoundException("No task found"));
        for (Long resourceId : resources){
           Resource resource  = resourceRepository.findById(resourceId).orElseThrow(()-> new EntityNotFoundException("No resource found"));
           User receiver = userRepository.findById(addResourceToTaskRequest.getReceiverId()).orElseThrow(()-> new EntityNotFoundException("No user found for receiver"));
           if (resource.getStatus() != EResourceStatus.AVAILABLE){
               log.error("Resource is not available");
               return ResponseEntity.badRequest().body("Resource is not available");
           }
           resource.setTask(task);
           resource.setReceiver(receiver);
           //notify receiver
            String bodyMessage = String.format(NotificationMessages.RESOURCE_IS_BEING_SENT, resource.getId());
            notificationService.sendNotification("Resource is being sent", bodyMessage, receiver.getId(), resource.getId(), ENotificationEntityType.RESOURCE);

           resource.setStatus(EResourceStatus.IN_TASK);
           resourceRepository.save(resource);
        }
        task.getResources().addAll(resourceRepository.findAllById(resources));
        taskRepository.save(task);
        return ResponseEntity.ok("Resources added successfully to Task");
    }

    @Transactional
    public ResponseEntity<String> removeResources(AddResourceToTaskRequest addResourceToTaskRequest){
        Long taskId = addResourceToTaskRequest.getTaskId();
        List<Long> resources = addResourceToTaskRequest.getResourceIds();
        Task task  = taskRepository.findById(taskId).orElseThrow(()-> new EntityNotFoundException("No task found"));
        for (Long resourceId : resources){
            Resource resource  = resourceRepository.findById(resourceId).orElseThrow(()-> new EntityNotFoundException("No resource found"));
            resource.setTask(null);
            resource.setReceiver(null);
            resource.setStatus(EResourceStatus.AVAILABLE);
            // TODO: notify receiver
            resourceRepository.save(resource);
        }
        resourceRepository.findAllById(resources)
                .forEach(resource -> task.getResources().remove(resource));
        taskRepository.save(task);
        return ResponseEntity.ok("Resources removed successfully from Task");


    }


    public ResponseEntity<List<TaskDto>> viewTasksByFilter(Long assignerId, Long assigneeId, EUrgency urgency, EStatus status) {
        Specification<Task> spec = Specification.where(null);

        if (assignerId != null) {
            spec = spec.and(TaskSpecifications.hasAssigner(assignerId));
        }

        if (assigneeId != null) {
            spec = spec.and(TaskSpecifications.hasAssignee(assigneeId));
        }
        if (urgency != null){
            spec = spec.and(TaskSpecifications.hasUrgency(urgency));
        }

        if(status != null) {
            spec = spec.and(TaskSpecifications.hasStatus(status));
        }

        return ResponseEntity.ok(taskRepository.findAll(spec).stream().map(task -> taskConverter.convertToDto(task)).toList());

    }

    @Transactional
    public ResponseEntity<String> addRequestToTask(AddReqToTaskRequest addReqToTaskRequest){
        Long taskId = addReqToTaskRequest.getTaskId();
        List<Long> requestIds = addReqToTaskRequest.getRequestIds();
        Task task = taskRepository.findById(taskId).orElseThrow(()-> new EntityNotFoundException("No task found"));
        requestIds.stream().forEach(id->{
            task.getRequests().add(requestRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("No request found for task")));
            Request req = requestRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("No request found for task"));
            req.setTask(task);
            req.setStatus(ERequestStatus.IN_TASK);
            requestRepository.save(req);
        });
        taskRepository.save(task);
        return ResponseEntity.ok("Request added successfully to Task");
    }

    @Transactional
    public ResponseEntity<String> removeRequestFromTask(AddReqToTaskRequest removeReqFromTask){
        Long taskId = removeReqFromTask.getTaskId();
        List<Long> requestIds = removeReqFromTask.getRequestIds();
        Task task = taskRepository.findById(taskId).orElseThrow(()-> new EntityNotFoundException("No task found"));
        requestIds.stream().forEach(id->{
            task.getRequests().remove(requestRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("No request found for task")));
            Request req = requestRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("No request found for task"));
            req.setTask(null);
            req.setStatus(ERequestStatus.PENDING);
            requestRepository.save(req);
        });
        taskRepository.save(task);
        return ResponseEntity.ok("Request removed successfully from Task");

}





}
