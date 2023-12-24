package com.groupa1.resq.service;


import com.groupa1.resq.converter.ActionConverter;
import com.groupa1.resq.entity.Action;
import com.groupa1.resq.entity.Task;
import com.groupa1.resq.entity.User;
import com.groupa1.resq.repository.ActionRepository;
import com.groupa1.resq.repository.TaskRepository;
import com.groupa1.resq.repository.UserRepository;
import com.groupa1.resq.request.CreateActionRequest;
import com.groupa1.resq.dto.ActionDto;
import com.groupa1.resq.request.CreateCommentRequest;
import com.groupa1.resq.request.UpdateActionRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import com.groupa1.resq.entity.enums.ENotificationEntityType;
import com.groupa1.resq.entity.Comment;
import java.util.Arrays;
import java.util.List;




import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

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

    @Mock
    private NotificationService notificationService;

    @Mock
    private ActionConverter actionConverter;


    @Test
    void test_create_action(){
        // given
        CreateActionRequest createActionRequest = new CreateActionRequest();

        Long taskId = 1L;
        createActionRequest.setTaskId(taskId);

        Task task = new Task();
        User verifier = new User();
        Action savedAction = new Action();

        when(taskRepository.existsById(taskId)).thenReturn(true);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(userRepository.findById(any())).thenReturn(Optional.of(verifier));
        when(actionRepository.save(any())).thenReturn(savedAction);

        // when
        ResponseEntity<Object> responseEntity = actionService.createAction(createActionRequest);

        // then
        verify(taskRepository, times(1)).existsById(taskId);
        verify(userRepository, times(1)).findById(any());
        verify(taskRepository, times(1)).save(task);
        verify(actionRepository, times(1)).save(any());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void test_view_single_action(){
        // given
        Action action = new Action();
        Task task = new Task();

        action.setTask(task);

        Long actionId = 1L;
        when(actionRepository.findById(actionId)).thenReturn(Optional.of(action));

        // when
        ResponseEntity<ActionDto> responseEntity = actionService.viewSingleAction(actionId);

        // then
        verify(actionRepository, times(1)).findById(actionId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void test_delete_action(){
        // given
        Action action = new Action();
        Task task = new Task();

        action.setTask(task);

        Long actionId = 1L;
        when(actionRepository.findById(actionId)).thenReturn(Optional.of(action));

        // when
        ResponseEntity<String> responseEntity = actionService.deleteAction(actionId);

        // then
        verify(actionRepository, times(1)).findById(actionId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Action deleted successfully", responseEntity.getBody());
    }

    @Test
    void test_updateAction() {
        // Given
        UpdateActionRequest updateActionRequest = new UpdateActionRequest();
        User verifier = new User();
        Action action = new Action();

        Long actionId = 1L;
        updateActionRequest.setVerifierId(2L);

        when(actionRepository.findById(actionId)).thenReturn(Optional.of(action));
        when(userRepository.findById(any())).thenReturn(Optional.of(verifier));

        // When
        ResponseEntity<String> responseEntity = actionService.updateAction(updateActionRequest, actionId);

        // Then
        verify(actionRepository, times(1)).findById(actionId);
        verify(userRepository, times(1)).findById(any());
        verify(actionRepository, times(1)).save(action);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Action updated successfully", responseEntity.getBody());
    }

    @Test
    void test_completeAction() {
        // Given
        Long actionId = 1L;
        Long userId = 2L;

        Action action = new Action();
        User user = new User();
        User verifier = new User();
        Task task = new Task();

        action.setVerifier(verifier);
        user.setId(userId);
        action.setId(actionId);
        task.setAssignee(user);
        action.setTask(task);

        when(actionRepository.findById(actionId)).thenReturn(Optional.of(action));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        ResponseEntity<String> responseEntity = actionService.completeAction(actionId, userId);

        // Then
        verify(actionRepository, times(1)).findById(actionId);
        verify(userRepository, times(1)).findById(userId);
        verify(actionRepository, times(1)).save(action);
        verify(notificationService, times(1)).sendNotification(
                eq("Request Created"),
                anyString(),
                eq(action.getVerifier().getId()),
                eq(action.getId()),
                eq(ENotificationEntityType.RESOURCE)
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Action completed by responder", responseEntity.getBody());
    }

    @Test
    void test_verifyAction() {
        // Given
        Long actionId = 1L;
        Long userId = 2L;

        Action action = new Action();
        User user = new User();
        User verifier = new User();

        action.setId(actionId);
        verifier.setId(userId);
        action.setVerifier(verifier);

        when(actionRepository.findById(actionId)).thenReturn(Optional.of(action));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        ResponseEntity<String> responseEntity = actionService.verifyAction(actionId, userId);

        // Then
        verify(actionRepository, times(1)).findById(actionId);
        verify(userRepository, times(1)).findById(userId);
        verify(actionRepository, times(1)).save(action);
        verify(notificationService, times(1)).sendNotification(
                eq("Action Verified"),
                anyString(),
                eq(user.getId()),
                eq(action.getId()),
                eq(ENotificationEntityType.TASK)
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Action verified by facilitator", responseEntity.getBody());
    }

    @Test
    void test_commentAction() {
        // Given
        CreateCommentRequest commentActionRequest = new CreateCommentRequest();
        commentActionRequest.setUserId(1L);
        commentActionRequest.setActionId(2L);
        commentActionRequest.setComment("This is a comment.");

        User user = new User();
        Action action = new Action();
        User verifier = new User();

        user.setId(commentActionRequest.getUserId());
        verifier.setId(commentActionRequest.getUserId());
        action.setVerifier(verifier);

        when(userRepository.findById(commentActionRequest.getUserId())).thenReturn(Optional.of(user));
        when(actionRepository.findById(commentActionRequest.getActionId())).thenReturn(Optional.of(action));

        // When
        ResponseEntity<String> responseEntity = actionService.commentAction(commentActionRequest);

        // Then
        verify(userRepository, times(1)).findById(commentActionRequest.getUserId());
        verify(actionRepository, times(1)).findById(commentActionRequest.getActionId());

        assertEquals(1, action.getComments().size());
        Comment addedComment = action.getComments().iterator().next();
        assertEquals(user, addedComment.getVerifier());
        assertEquals(action, addedComment.getAction());
        assertEquals(commentActionRequest.getComment(), addedComment.getComment());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Comment added successfully", responseEntity.getBody());
    }

    @Test
    void test_viewActionsByFilter() {
        // Given
        Long verifierId = 1L;
        boolean isCompleted = true;
        Long taskId = 2L;
        boolean isVerified = false;

        Action action1 = new Action();
        Action action2 = new Action();
        Task task = new Task();

        task.setId(taskId);
        action1.setCompleted(isCompleted);
        action1.setVerified(isVerified);
        action1.setTask(task);
        action2.setCompleted(isCompleted);
        action2.setVerified(isVerified);
        action2.setTask(task);

        when(actionRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(action1, action2));

        // When
        ResponseEntity<List<ActionDto>> responseEntity = actionService.viewActionsByFilter(
                verifierId, isCompleted, null, null, taskId, isVerified);

        // Then
        verify(actionRepository, times(1)).findAll(any(Specification.class));
        verify(actionConverter, times(2)).convertToDto(any());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(2, responseEntity.getBody().size());
    }
}
