package com.groupa1.resq.service;

import com.groupa1.resq.converter.NeedConverter;
import com.groupa1.resq.dto.NeedDto;
import com.groupa1.resq.entity.Need;
import com.groupa1.resq.entity.Request;
import com.groupa1.resq.entity.User;
import com.groupa1.resq.entity.enums.ENeedStatus;
import com.groupa1.resq.entity.enums.ENotificationEntityType;
import com.groupa1.resq.entity.enums.EStatus;
import com.groupa1.resq.entity.enums.EUrgency;
import com.groupa1.resq.repository.NeedRepository;
import com.groupa1.resq.repository.RequestRepository;
import com.groupa1.resq.repository.UserRepository;
import com.groupa1.resq.request.CreateNeedRequest;
import com.groupa1.resq.request.CreateReqRequest;
import com.groupa1.resq.request.UpdateNeedRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NeedServiceTest {

    @InjectMocks
    private NeedService needService;

    @Mock
    private NeedRepository needRepository;

    @Mock
    private UserRepository userRepository;


    @Test
    void test_save() {
        // Given
        Long userId = 1L;
        CreateNeedRequest createNeedRequest = new CreateNeedRequest();
        User requester = new User();
        Need need = new Need();
        NeedService needService = new NeedService();
        UserRepository userRepository = mock(UserRepository.class);
        NeedRepository needRepository = mock(NeedRepository.class);
        need.setRequester(requester);
        needService.setUserRepository(userRepository);
        needService.setNeedRepository(needRepository);
        when(userRepository.findById(userId)).thenReturn(Optional.of(requester));
        when(needRepository.save(any())).thenReturn(need);

        // when
        needService.save(userId, createNeedRequest);

        // then
        verify(userRepository, times(1)).findById(userId);
        verify(needRepository, times(1)).save(any());
    }

    @Test
    void test_viewAllNeeds() {
        // Given
        // when
        needService.viewAllNeeds();
        // then
        verify(needRepository, times(1)).findAll();
    }

    @Test
    void test_viewNeedsByUserId() {
        // Given
        User user = new User();
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        // when
        needService.viewNeedsByUserId(1L);
        // then
        verify(needRepository, times(1)).findByRequester(any());
    }

    @Test
    void test_view_need() {
        // Given
        User user = new User();
        Need need = new Need();
        NeedConverter needConverterMock = mock(NeedConverter.class);
        NeedService needService = new NeedService();
        needService.setNeedConverter(needConverterMock);
        needService.setUserRepository(userRepository);
        needService.setNeedRepository(needRepository);
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(needRepository.findById(any(Long.class))).thenReturn(Optional.of(need));
        need.setRequester(user);
        when(needConverterMock.convertToDto(any(Need.class))).thenReturn(new NeedDto());
        // when
        needService.viewNeed(1L, 1L);
        // then
        verify(needRepository, times(1)).findById(any());
    }

    @Test
    void test_delete_need() {
        // Given
        User user = new User();
        Need need = new Need();
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(needRepository.findById(any(Long.class))).thenReturn(Optional.of(need));
        need.setRequester(user);
        // when
        needService.deleteNeed(1L, 1L);
        // then
        verify(needRepository, times(1)).deleteById(any());
    }

    @Test
    void test_update() {
        // Given
        User user = new User();
        Need need = new Need();
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(needRepository.findById(any(Long.class))).thenReturn(Optional.of(need));
        need.setRequester(user);
        // when
        needService.update(new UpdateNeedRequest(), 1L, 1L);
        // then
        verify(needRepository, times(1)).save(any());
    }

    @Test
    void test_view_needs_by_filter() {
        // Given
        User user = new User();
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        // when
        needService.viewNeedsByFilter(BigDecimal.valueOf(1), BigDecimal.valueOf(1), BigDecimal.valueOf(1), BigDecimal.valueOf(1),"1", 1L);
        // then
        verify(needRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    void test_cancel_need() {
        // Given
        Need need = new Need();
        when(needRepository.findById(any(Long.class))).thenReturn(Optional.of(need));
        // when
        needService.cancelNeed(1L);
        // then
        verify(needRepository, times(1)).save(any());
    }

    @Test
    void test_filter_by_distance() {
        // Given
        // when
        needService.filterByDistance(BigDecimal.valueOf(1), BigDecimal.valueOf(1), BigDecimal.valueOf(1));
        // then
        verify(needRepository, times(1)).filterByDistance(any(), any(), any());
    }




}
