package com.groupa1.resq.service;

import com.groupa1.resq.entity.User;
import com.groupa1.resq.entity.enums.EUserRole;
import com.groupa1.resq.exception.EntityNotFoundException;
import com.groupa1.resq.repository.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void test_whenExistsByEmail_shouldReturnTrue() {
        // Given
        User mockUser = new User();
        mockUser.setEmail("test-email");
        // when

        when(userRepository.existsByEmail("test-email")).thenReturn(true);
        //then
        Boolean result = userService.existsByEmail("test-email");
        assertEquals(true, result);
    }

    @Test
    void test_whenNotExistsByEmail_shouldReturnFalse() {
        // Given
        // when
        when(userRepository.existsByEmail("test-email")).thenReturn(false);
        //then
        Boolean result = userService.existsByEmail("test-email");
        assertEquals(false, result);
    }

    @Test
    void test_findByEmail_whenExists_shouldReturnOptionalOfUser() {
        // Given
        User mockUser = new User();
        mockUser.setEmail("test-email");
        Optional<User> optionalMockUser = Optional.of(mockUser);

        // when
        when(userRepository.findByEmail("test-email")).thenReturn(optionalMockUser);
        // then
        Optional<User> result = userService.findByEmail("test-email");
        assertEquals(optionalMockUser, result);
    }

    @Test
    void test_findByEmail_whenNotExists_shouldReturnOptionalOfEmpty() {
        // Given
        // when
        when(userRepository.findByEmail("test-email")).thenReturn(Optional.empty());
        // then
        Optional<User> result = userService.findByEmail("test-email");
        assertEquals(Optional.empty(), result);
    }

    @Test
    void test_requestRole_whenSuccess_shouldAddRoleToTheUsersRoleSet() {
        // Given
        User mockUserWithOneRole = new User();
        Long mockUserId = 1L;
        mockUserWithOneRole.setId(mockUserId);
        mockUserWithOneRole.getRoles().add(EUserRole.VICTIM);

        User mockUpdatedUserWithTwoRole = new User();
        mockUpdatedUserWithTwoRole.setId(mockUserId);
        mockUpdatedUserWithTwoRole.getRoles().add(EUserRole.VICTIM);
        mockUpdatedUserWithTwoRole.getRoles().add(EUserRole.RESPONDER);

        // when
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUserWithOneRole));
        when(userRepository.save(mockUserWithOneRole)).thenReturn(mockUpdatedUserWithTwoRole);

        // then
        userService.requestRole(mockUserId, "RESPONDER");
        assertTrue(mockUserWithOneRole.getRoles().contains(EUserRole.RESPONDER));
        assertTrue(mockUserWithOneRole.getRoles().contains(EUserRole.VICTIM));
        assertTrue(mockUserWithOneRole.getRoles().size() == 2);
    }

    @Test
    void test_findById_whenSuccess_returnUser() {
        // Given
        User mockUser = new User();
        Long mockUserId = 1L;
        mockUser.setId(mockUserId);

        // when
        when(userRepository.findById(mockUserId)).thenReturn(Optional.of(mockUser));

        // then
        User result = userService.findById(mockUserId);
        assertEquals(mockUser, result);
    }

    @Test
    void test_findById_whenFails_shouldThrowEntityNotFoundException() {
        // Given
        Long mockUserId = 1L;

        // when
        when(userRepository.findById(mockUserId)).thenReturn(Optional.empty());

        // then
        assertThrows(EntityNotFoundException.class, () -> userService.findById(mockUserId));
    }

    @Test
    void testSave_ifSuccess_shouldSaveAndGiveIdToUser() {
        // Given
        User mockUser = new User();

        User mockUserWithId = new User();
        mockUserWithId.setId(1L);

        // when
        when(userRepository.save(mockUser)).thenReturn(mockUserWithId);

        // then
        User result = userService.save(mockUser);
        assertEquals(mockUserWithId, result);
        assertNotNull(result.getId());
    }
}