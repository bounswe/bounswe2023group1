package com.groupa1.resq.service;

import com.groupa1.resq.entity.User;
import com.groupa1.resq.entity.Request;
import com.groupa1.resq.repository.RequestRepository;
import com.groupa1.resq.repository.UserRepository;
import com.groupa1.resq.request.CreateReqRequest;
import com.groupa1.resq.request.UpdateReqRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Optional;
import static org.mockito.Mockito.*;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {

    @InjectMocks
    private RequestService requestService;

    @Mock
    private RequestRepository requestRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    void test_save() {
        // Given
        CreateReqRequest createReqRequest = new CreateReqRequest();
        User user = new User();
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));

        // when
        requestService.save(1L, createReqRequest);
        // then
        verify(requestRepository, times(1)).save(any());
    }

    @Test
    void test_viewAllRequests() {
        // Given
        // when
        requestService.viewAllRequests();
        // then
        verify(requestRepository, times(1)).findAll();
    }

    @Test
    void test_viewRequestsByFilter() {
        // Given
        User user = new User();
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        // when
        requestService.viewRequestsByFilter(BigDecimal.valueOf(1), BigDecimal.valueOf(1), null, null, 1L);
        // then
        verify(requestRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    void test_update() {
        // Given
        UpdateReqRequest updateReqRequest = new UpdateReqRequest();
        User user = new User();
        Request request = new Request();
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(requestRepository.findById(any(Long.class))).thenReturn(Optional.of(request));
        request.setRequester(user);
        // when
        requestService.update(updateReqRequest, 1L, 1L);
        // then
        verify(requestRepository, times(1)).save(any());
    }

    @Test
    void test_deleteRequest() {
        // Given
        User user = new User();
        Request request = new Request();
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(requestRepository.findById(any(Long.class))).thenReturn(Optional.of(request));
        request.setRequester(user);
        // when
        requestService.deleteRequest(1L, 1L);
        // then
        verify(requestRepository, times(1)).deleteById(any());
    }


}
