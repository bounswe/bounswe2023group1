package com.groupa1.resq.service;


import com.groupa1.resq.converter.TaskConverter;
import com.groupa1.resq.entity.Task;
import com.groupa1.resq.entity.User;
import com.groupa1.resq.entity.Action;
import com.groupa1.resq.entity.Resource;
import com.groupa1.resq.request.AddResourceToTaskRequest;
import com.groupa1.resq.entity.enums.EUrgency;
import com.groupa1.resq.repository.ActionRepository;
import com.groupa1.resq.repository.TaskRepository;
import com.groupa1.resq.repository.UserRepository;
import com.groupa1.resq.entity.enums.EResourceStatus;
import com.groupa1.resq.repository.ResourceRepository;
import com.groupa1.resq.request.CreateTaskRequest;
import com.groupa1.resq.request.UpdateTaskRequest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import java.util.*;
import org.springframework.http.HttpStatus;
import com.groupa1.resq.entity.enums.EStatus;
import org.mockito.ArgumentCaptor;
import com.groupa1.resq.dto.TaskDto;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {


    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ActionRepository actionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private NotificationService notificationService;

    @Mock
    private UserService userService;

    @Mock
    private TaskConverter taskConverter;

    @Mock
    private ResourceRepository resourceRepository;






    @Test
    void testAcceptTask_withExistentTask_shouldReturnSuccess() {
        // Given
        Task task = new Task();
        User user = new User();
        task.setId(1L);
        user.setId(2L);
        task.setAssignee(user);

        // When
        when(taskRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(task));
        when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));

        ResponseEntity<String> response = taskService.acceptTask(1L, 2L);

        assertEquals("Task accepted", response.getBody());

    }

    @Test
    void testAcceptTask_withNonExistentTask_shouldReturnBadRequest() {
        // Given
        Long taskId = 1L;
        Long userId = 1L;


        // when
        when(taskRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // then
        ResponseEntity<String> response = taskService.acceptTask(taskId, userId);
        assertEquals("Task not found", response.getBody());
    }

    @Transactional
    @Test
    void test_createTask() {
        // Given
        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setAssignerId(1L);
        createTaskRequest.setAssigneeId(2L);
        createTaskRequest.setUrgency(EUrgency.HIGH);
        createTaskRequest.setDescription("Task description");

        User assigner = new User();
        User assignee = new User();
        Task savedTask = new Task();

        assigner.setId(createTaskRequest.getAssignerId());
        assignee.setId(createTaskRequest.getAssigneeId());

        when(userService.findById(createTaskRequest.getAssignerId())).thenReturn(assigner);
        when(userService.findById(createTaskRequest.getAssigneeId())).thenReturn(assignee);
        when(taskRepository.save(any())).thenReturn(savedTask);

        // When
        ResponseEntity<Object> responseEntity = taskService.createTask(createTaskRequest);

        // Then
        verify(userService, times(1)).findById(createTaskRequest.getAssignerId());
        verify(userService, times(1)).findById(createTaskRequest.getAssigneeId());
        verify(taskRepository, times(1)).save(any(Task.class));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void test_acceptTask() {
        // Given
        Long taskId = 1L;
        Long userId = 2L;

        Task task = new Task();
        task.setId(taskId);
        task.setStatus(EStatus.PENDING);

        User assignee = new User();
        assignee.setId(userId);
        task.setAssignee(assignee);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(userRepository.findById(userId)).thenReturn(Optional.of(assignee));

        // When
        ResponseEntity<String> responseEntity = taskService.acceptTask(taskId, userId);

        // Then
        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository, times(1)).save(taskCaptor.capture());
        Task savedTask = taskCaptor.getValue();
        assertEquals(EStatus.TODO, savedTask.getStatus());
        assertEquals(assignee, savedTask.getAssignee());
        verify(taskRepository, times(1)).findById(taskId);
        verify(userRepository, times(1)).findById(userId);
        verify(taskRepository, times(1)).save(task);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Task accepted", responseEntity.getBody());
    }

    @Test
    void test_declineTask() {
        // Given
        Long taskId = 1L;
        Long userId = 2L;

        Task task = new Task();
        task.setId(taskId);
        task.setStatus(EStatus.PENDING);

        User assignee = new User();
        assignee.setId(userId);
        task.setAssignee(assignee);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(userRepository.findById(userId)).thenReturn(Optional.of(assignee));

        // When
        ResponseEntity<String> responseEntity = taskService.declineTask(taskId, userId);

        // Then
        verify(taskRepository, times(1)).findById(taskId);
        verify(userRepository, times(1)).findById(userId);
        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository, times(1)).save(taskCaptor.capture());
        Task savedTask = taskCaptor.getValue();
        assertEquals(EStatus.DECLINED, savedTask.getStatus());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Task declined", responseEntity.getBody());
    }

    @Test
    void test_viewSingleTask() {
        // Given
        Long taskId = 1L;

        Task task = new Task();
        task.setId(taskId);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        // When
        ResponseEntity<TaskDto> responseEntity = taskService.viewSingleTask(taskId);

        // Then
        verify(taskRepository, times(1)).findById(taskId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void test_viewTasks() {
        // Given
        Long userId = 1L;

        Task task1 = new Task();
        task1.setId(1L);

        Task task2 = new Task();
        task2.setId(2L);

        List<Task> taskList = Arrays.asList(task1, task2);

        when(taskRepository.findByAssignee(userId)).thenReturn(Optional.of(taskList));

        // When
        ResponseEntity<List<TaskDto>> responseEntity = taskService.viewTasks(userId);

        // Then
        verify(taskRepository, times(1)).findByAssignee(userId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void test_deleteTask() {
        // Given
        Long taskId = 1L;

        Task task = new Task();
        task.setId(taskId);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        // When
        ResponseEntity<String> responseEntity = taskService.deleteTask(taskId);

        // Then
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).delete(task);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Task deleted successfully", responseEntity.getBody());
    }

    @Test
    void test_updateTask() {
        // Given
        Long taskId = 1L;

        UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest();
        updateTaskRequest.setDescription("Updated Description");
        updateTaskRequest.setUrgency(EUrgency.HIGH);
        updateTaskRequest.setStatus(EStatus.IN_PROGRESS);

        Task task = new Task();
        task.setId(taskId);
        task.setDescription("Original Description");
        task.setUrgency(EUrgency.LOW);
        task.setStatus(EStatus.PENDING);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        // When
        ResponseEntity<String> responseEntity = taskService.updateTask(updateTaskRequest, taskId);

        // Then
        verify(taskRepository, times(1)).findById(taskId);
        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository, times(1)).save(taskCaptor.capture());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Task updated successfully", responseEntity.getBody());
        assertEquals("Updated Description", taskCaptor.getValue().getDescription());
        assertEquals(EUrgency.HIGH, taskCaptor.getValue().getUrgency());
        assertEquals(EStatus.IN_PROGRESS, taskCaptor.getValue().getStatus());
    }

    @Test
    void test_assignTask() {
        // Given
        Long taskId = 1L;
        Long userId = 2L;

        Task task = new Task();
        task.setId(taskId);
        task.setStatus(EStatus.PENDING);

        User user = new User();
        user.setId(userId);
        task.setAssigner(user);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        ResponseEntity<String> responseEntity = taskService.assignTask(taskId, userId);

        // Then
        verify(taskRepository, times(1)).findById(taskId);
        verify(userRepository, times(1)).findById(userId);
        verify(taskRepository, times(1)).save(task);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Task assigned successfully", responseEntity.getBody());
        assertEquals(user, task.getAssignee());
        assertEquals(EStatus.PENDING, task.getStatus());
    }

    @Test
    void test_unassignTask() {
        // Given
        Long taskId = 1L;

        Task task = new Task();
        task.setId(taskId);
        task.setStatus(EStatus.PENDING);

        User assignee = new User();
        assignee.setId(2L);

        User assigner = new User();
        assigner.setId(3L);

        task.setAssignee(assignee);
        task.setAssigner(assigner);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(userRepository.findById(assignee.getId())).thenReturn(Optional.of(assignee));

        // When
        ResponseEntity<String> responseEntity = taskService.unassignTask(taskId);

        // Then
        verify(taskRepository, times(1)).findById(taskId);
        verify(userRepository, times(1)).findById(assignee.getId());
        verify(taskRepository, times(1)).save(task);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Task unassigned successfully", responseEntity.getBody());
        assertNull(task.getAssignee());
        assertEquals(EStatus.FREE, task.getStatus());
    }

    @Test
    void test_completeTask() {
        // Given
        Long taskId = 1L;
        Long userId = 2L;

        Task task = new Task();
        task.setId(taskId);
        task.setStatus(EStatus.TODO);

        User assignee = new User();
        assignee.setId(userId);

        Action action1 = new Action();
        action1.setCompleted(true);
        action1.setId(1L);

        Action action2 = new Action();
        action2.setCompleted(true);
        action2.setId(2L);

        task.setAssignee(assignee);
        Set<Action> actions = new HashSet<>();
        actions.add(action1);
        actions.add(action2);
        task.setActions(actions);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(userRepository.findById(userId)).thenReturn(Optional.of(assignee));

        // When
        ResponseEntity<String> responseEntity = taskService.completeTask(taskId, userId);

        // Then
        verify(taskRepository, times(1)).findById(taskId);
        verify(userRepository, times(1)).findById(userId);
        verify(taskRepository, times(1)).save(any(Task.class));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Task completed", responseEntity.getBody());
        assertEquals(EStatus.DONE, task.getStatus());
        assertTrue(task.getActions().stream().allMatch(Action::isCompleted));
    }

    @Test
    void test_addResources() {
        // Given
        Long taskId = 1L;
        Long receiverId = 2L;
        List<Long> resourceIds = List.of(3L, 4L);

        Task task = new Task();
        task.setId(taskId);

        User receiver = new User();
        receiver.setId(receiverId);

        Resource resource1 = new Resource();
        resource1.setId(3L);
        resource1.setStatus(EResourceStatus.AVAILABLE);

        Resource resource2 = new Resource();
        resource2.setId(4L);
        resource2.setStatus(EResourceStatus.AVAILABLE);

        AddResourceToTaskRequest addResourceToTaskRequest = new AddResourceToTaskRequest();
        addResourceToTaskRequest.setTaskId(taskId);
        addResourceToTaskRequest.setReceiverId(receiverId);
        addResourceToTaskRequest.setResourceIds(resourceIds);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(userRepository.findById(receiverId)).thenReturn(Optional.of(receiver));
        when(resourceRepository.findById(3L)).thenReturn(Optional.of(resource1));
        when(resourceRepository.findById(4L)).thenReturn(Optional.of(resource2));
        when(resourceRepository.findAllById(resourceIds)).thenReturn(List.of(resource1, resource2));

        // When
        ResponseEntity<String> responseEntity = taskService.addResources(addResourceToTaskRequest);

        // Then
        verify(taskRepository, times(1)).findById(taskId);
        verify(userRepository, times(2)).findById(receiverId);
        verify(resourceRepository, times(1)).findById(3L);
        verify(resourceRepository, times(1)).findById(4L);
        verify(resourceRepository, times(1)).findAllById(resourceIds);
        verify(notificationService, times(2)).sendNotification(anyString(), anyString(), anyLong(), anyLong(), any());
        ArgumentCaptor<Resource> resourceCaptor = ArgumentCaptor.forClass(Resource.class);
        verify(resourceRepository, times(2)).save(resourceCaptor.capture());
        List<Resource> capturedResources = resourceCaptor.getAllValues();
        assertEquals(EResourceStatus.IN_TASK, capturedResources.get(0).getStatus());
        assertEquals(EResourceStatus.IN_TASK, capturedResources.get(1).getStatus());
        assertEquals(task, capturedResources.get(0).getTask());
        assertEquals(task, capturedResources.get(1).getTask());
        verify(taskRepository, times(1)).save(task);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Resources added successfully to Task", responseEntity.getBody());
    }

    @Test
    void test_removeResources() {
        // Given
        Long taskId = 1L;
        List<Long> resourceIds = List.of(3L, 4L);

        Task task = new Task();
        task.setId(taskId);

        Resource resource1 = new Resource();
        resource1.setId(3L);
        resource1.setStatus(EResourceStatus.IN_TASK);
        resource1.setTask(task);

        Resource resource2 = new Resource();
        resource2.setId(4L);
        resource2.setStatus(EResourceStatus.IN_TASK);
        resource2.setTask(task);

        AddResourceToTaskRequest addResourceToTaskRequest = new AddResourceToTaskRequest();
        addResourceToTaskRequest.setTaskId(taskId);
        addResourceToTaskRequest.setResourceIds(resourceIds);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(resourceRepository.findById(3L)).thenReturn(Optional.of(resource1));
        when(resourceRepository.findById(4L)).thenReturn(Optional.of(resource2));
        when(resourceRepository.findAllById(resourceIds)).thenReturn(List.of(resource1, resource2));

        // When
        ResponseEntity<String> responseEntity = taskService.removeResources(addResourceToTaskRequest);

        // Then
        verify(taskRepository, times(1)).findById(taskId);
        verify(resourceRepository, times(1)).findById(3L);
        verify(resourceRepository, times(1)).findById(4L);
        verify(resourceRepository, times(1)).findAllById(resourceIds);
        ArgumentCaptor<Resource> resourceCaptor = ArgumentCaptor.forClass(Resource.class);
        verify(resourceRepository, times(2)).save(resourceCaptor.capture());
        List<Resource> capturedResources = resourceCaptor.getAllValues();
        assertEquals(EResourceStatus.AVAILABLE, capturedResources.get(0).getStatus());
        assertNull(capturedResources.get(0).getTask());
        assertNull(capturedResources.get(0).getReceiver());
        assertEquals(EResourceStatus.AVAILABLE, capturedResources.get(1).getStatus());
        assertNull(capturedResources.get(1).getTask());
        assertNull(capturedResources.get(1).getReceiver());
        verify(taskRepository, times(1)).save(task);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Resources removed successfully from Task", responseEntity.getBody());
    }

    @Test
    void test_viewTasksByFilter() {
        // Given
        Long assignerId = 1L;
        Long assigneeId = 2L;
        EUrgency urgency = EUrgency.HIGH;
        EStatus status = EStatus.PENDING;

        Task task1 = new Task();
        task1.setId(1L);
        task1.setAssigner(new User());
        task1.setAssignee(new User());
        task1.setUrgency(EUrgency.HIGH);
        task1.setStatus(EStatus.PENDING);

        Task task2 = new Task();
        task2.setId(2L);
        task2.setAssigner(new User());
        task2.setAssignee(new User());
        task2.setUrgency(EUrgency.MEDIUM);
        task2.setStatus(EStatus.IN_PROGRESS);

        Task task3 = new Task();
        task3.setId(3L);
        task3.setAssigner(new User());
        task3.setAssignee(new User());
        task3.setUrgency(EUrgency.HIGH);
        task3.setStatus(EStatus.PENDING);

        when(taskRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(task1, task3));

        // When
        ResponseEntity<List<TaskDto>> responseEntity = taskService.viewTasksByFilter(assignerId, assigneeId, urgency, status);

        // Then
        verify(taskRepository, times(1)).findAll(any(Specification.class));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(2, responseEntity.getBody().size());
    }
}
