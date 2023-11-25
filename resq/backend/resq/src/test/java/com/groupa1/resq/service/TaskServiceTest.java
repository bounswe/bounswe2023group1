package com.groupa1.resq.service;


import com.groupa1.resq.entity.Task;
import com.groupa1.resq.entity.User;
import com.groupa1.resq.entity.enums.EUrgency;
import com.groupa1.resq.repository.TaskRepository;
import com.groupa1.resq.repository.UserRepository;
import com.groupa1.resq.request.CreateTaskRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {


    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;



    @Test
    void testCreateTask_withEmptyActions_withEmptyResources_shouldReturnSuccess() {
        // Given
        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setActions(new ArrayList<>());
        createTaskRequest.setResources(new ArrayList<>());
        createTaskRequest.setAssigneeId(1L);
        createTaskRequest.setAssignerId(2L);
        createTaskRequest.setDescription("Test description");
        createTaskRequest.setUrgency(EUrgency.HIGH);


        // when
        when(userService.findById(createTaskRequest.getAssigneeId())).thenReturn(new User());
        when(userService.findById(createTaskRequest.getAssignerId())).thenReturn(new User());


        // then
        ResponseEntity<String> response = taskService.createTask(createTaskRequest);
        assertEquals("Task saved successfully", response.getBody());
    }

    @Test
    void testCreateTask_withNonSpecifiedAssigneeAssigner_shouldReturnBadRequest() {
        // Given
        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setActions(new ArrayList<>());
        createTaskRequest.setResources(new ArrayList<>());
        createTaskRequest.setDescription("Test description");
        createTaskRequest.setUrgency(EUrgency.HIGH);



        // then
        ResponseEntity<String> response = taskService.createTask(createTaskRequest);
        assertEquals("Assignee and assigner must be specified",
                response.getBody());
    }

    @Test
    void testCreateTask_withNonEmptyAction_withEmptyResources_shouldReturnSuccess() {
        // Given
        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        ArrayList<CreateTaskRequest.Action> actions = new ArrayList<>();
        actions.add(new CreateTaskRequest.Action());
        createTaskRequest.setActions(actions);
        createTaskRequest.setResources(new ArrayList<>());
        createTaskRequest.setAssigneeId(1L);
        createTaskRequest.setAssignerId(2L);
        createTaskRequest.setDescription("Test description");
        createTaskRequest.setUrgency(EUrgency.HIGH);

        // when
        when(userService.findById(
                createTaskRequest.getAssigneeId())).thenReturn(new User());
        when(userService.findById(
                createTaskRequest.getAssignerId())).thenReturn(new User());

        // then
        ResponseEntity<String> response =
                taskService.createTask(createTaskRequest);
        assertEquals("Task saved successfully", response.getBody());


    }


    @Test
    void testAcceptTask_withExistentTask_shouldReturnSuccess() {
        // Given
        Task task = new Task();
        User user = new User();

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
}
