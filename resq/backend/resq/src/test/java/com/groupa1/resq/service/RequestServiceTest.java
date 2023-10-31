package com.groupa1.resq.service;

import com.groupa1.resq.entity.User;
import com.groupa1.resq.repository.RequestRepository;
import com.groupa1.resq.repository.UserRepository;
import com.groupa1.resq.request.CreateReqRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Optional;
import static org.mockito.Mockito.*;

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
    void test_viewRequestsByUser() {
        // Given
        User user = new User();
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        // when
        requestService.viewRequestsByUser(1L);
        // then
        verify(requestRepository, times(1)).findByRequester(any());
    }

    @Test
    void test_viewRequestsByLocation() {
        // Given
        // when
        requestService.viewRequestsByLocation(new BigDecimal(1), new BigDecimal(1));
        // then
        verify(requestRepository, times(1)).findByLongitudeAndLatitude(any(), any());
    }

}
