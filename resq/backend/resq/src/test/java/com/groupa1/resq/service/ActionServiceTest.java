package com.groupa1.resq.service;


import com.groupa1.resq.entity.Action;
import com.groupa1.resq.entity.Task;
import com.groupa1.resq.entity.User;
import com.groupa1.resq.repository.ActionRepository;
import com.groupa1.resq.repository.TaskRepository;
import com.groupa1.resq.repository.UserRepository;
import com.groupa1.resq.request.CreateActionRequest;
import com.groupa1.resq.dto.ActionDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ActionServiceTest {


    @InjectMocks
    private ActionService actionService;

    @Mock
    private ActionRepository actionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskRepository taskRepository;






    @Test
    void testViewActions_Success() {
        // Given
        Long taskId = 1L;
        Task task = new Task();

        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        task.setId(taskId);
        Action action = new Action();
        action.setId(1L);
        action.setTask(task);
        action.setVerifier(user);
        action.setDescription("Test Action");
        action.setDueDate(LocalDateTime.now());
        action.setStartLatitude(BigDecimal.valueOf(1.0));
        action.setStartLongitude(BigDecimal.valueOf(2.0));
        action.setEndLatitude(BigDecimal.valueOf(3.0));
        action.setEndLongitude(BigDecimal.valueOf(4.0));
        action.setCompleted(false);
        Set<Action> actions = new HashSet<>();
        actions.add(action);
        task.setActions(actions);

        when(taskRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(task));


        ResponseEntity<List<ActionDto>> response = actionService.viewActions(taskId);

        // Then
        assertEquals(1, response.getBody().size());
    }


}
