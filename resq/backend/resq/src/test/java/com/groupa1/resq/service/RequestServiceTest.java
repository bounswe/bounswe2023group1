package com.groupa1.resq.service;

import com.groupa1.resq.entity.Need;
import com.groupa1.resq.entity.User;
import com.groupa1.resq.entity.Request;
import com.groupa1.resq.entity.enums.ENotificationEntityType;
import com.groupa1.resq.entity.enums.EStatus;
import com.groupa1.resq.entity.enums.EUrgency;
import com.groupa1.resq.repository.NeedRepository;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        Long userId = 1L;
        CreateReqRequest createReqRequest = new CreateReqRequest();
        User requester = new User();
        Request request = new Request();
        Need need = new Need();
        RequestService requestService = new RequestService();
        UserRepository userRepository = mock(UserRepository.class);
        NeedRepository needRepository = mock(NeedRepository.class);
        RequestRepository requestRepository = mock(RequestRepository.class);
        NotificationService notificationService = mock(NotificationService.class);
        need.setRequester(requester);
        requestService.setUserRepository(userRepository);
        requestService.setNeedRepository(needRepository);
        requestService.setRequestRepository(requestRepository);
        requestService.setNotificationService(notificationService);
        when(userRepository.findById(userId)).thenReturn(Optional.of(requester));
        when(requestRepository.save(any())).thenReturn(request);
        when(needRepository.findAllById(any())).thenReturn(new ArrayList<>(Set.of(need)));

        // when
        Long result = requestService.save(userId, createReqRequest);

        // then
        verify(userRepository, times(1)).findById(userId);
        verify(requestRepository, times(1)).save(any());
        verify(needRepository, times(1)).findAllById(any());
        verify(needRepository, times(1)).saveAll(any());
        verify(notificationService, times(1)).sendNotification(
                eq("Request Created"),
                anyString(),
                eq(requester.getId()),
                eq(result),
                eq(ENotificationEntityType.REQUEST)
        );
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
        requestService.viewRequestsByFilter(BigDecimal.valueOf(1), BigDecimal.valueOf(1), BigDecimal.valueOf(1), BigDecimal.valueOf(1), EStatus.valueOf("DONE"), EUrgency.valueOf("MEDIUM"), 1L);
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

    @Test
    void test_filterByDistance() {
        // Given
        // when
        requestService.filterByDistance(BigDecimal.valueOf(1), BigDecimal.valueOf(1), BigDecimal.valueOf(1));
        // then
        verify(requestRepository, times(1)).filterByDistance(any(), any(), any());
    }


}
