package com.groupa1.resq.service;

import com.groupa1.resq.entity.Need;
import com.groupa1.resq.entity.User;
import com.groupa1.resq.repository.NeedRepository;
import com.groupa1.resq.repository.UserRepository;
import com.groupa1.resq.request.CreateNeedRequest;
import com.groupa1.resq.request.UpdateNeedRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
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
        CreateNeedRequest createNeedRequest = new CreateNeedRequest();
        User user = new User();
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));

        // when
        needService.save(1L, createNeedRequest);
        // then
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
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(needRepository.findById(any(Long.class))).thenReturn(Optional.of(need));
        need.setRequester(user);
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





}
