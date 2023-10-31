package com.groupa1.resq.service;

import com.groupa1.resq.auth.UserDetailsImpl;
import com.groupa1.resq.entity.User;
import com.groupa1.resq.entity.enums.EUserRole;
import com.groupa1.resq.request.LoginUserRequest;
import com.groupa1.resq.request.RegisterUserRequest;
import com.groupa1.resq.response.JwtResponse;
import com.groupa1.resq.security.jwt.JwtUtils;
import io.jsonwebtoken.lang.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private JwtUtils jwtUtils;

    @Test
    void testSignup_ifEmailAlreadyInUse_shouldReturnBadRequest() {
        // Given
        RegisterUserRequest mockRegisterUserRequest = new RegisterUserRequest();
        mockRegisterUserRequest.setEmail("test-email");

        // when
        when(userService.existsByEmail("test-email")).thenReturn(true);

        // then
        assertEquals("Error: Email is already in use!", authService.signup(mockRegisterUserRequest).getBody());
    }

    @Test
    void testSignup_ifEmailNotAlreadyInUse_success() {
        // Given
        RegisterUserRequest mockRegisterUserRequest = new RegisterUserRequest();
        mockRegisterUserRequest.setName("test-name");
        mockRegisterUserRequest.setSurname("test-surname");
        mockRegisterUserRequest.setEmail("test-email");
        mockRegisterUserRequest.setPassword("test-password");

        String encodedPassword = "encoded-test-password";

        User mockUser = new User();
        mockUser.setName("test-name");
        mockUser.setSurname("test-surname");
        mockUser.setEmail("test-email");
        mockUser.setPassword(encodedPassword);

        mockUser.setRoles(Set.of(EUserRole.VICTIM));

        // when
        when(userService.existsByEmail("test-email")).thenReturn(false);
        when(encoder.encode("test-password")).thenReturn(encodedPassword);
        when(userService.save(mockUser)).thenReturn(mockUser);

        // then
        assertEquals("User registered successfully!", authService.signup(mockRegisterUserRequest).getBody());
        assertNotEquals("test-password", mockUser.getPassword());
    }

    @Test
    void testSignin_ifPasswordWrong_throwBadCredentialsException() {
        // Given
        LoginUserRequest mockLoginUserRequest = new LoginUserRequest();
        mockLoginUserRequest.setEmail("test-email");
        mockLoginUserRequest.setPassword("test-false-password");

        User mockUser = new User();
        mockUser.setEmail("test-email");
        mockUser.setPassword("test-true-password");
        // when
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Bad credentials"));

        // then
        assertThrows(BadCredentialsException.class, () -> authService.signin(mockLoginUserRequest));
    }

    @Test
    void testSignin_ifPasswordTrue_returnJwtResponse() {
        // Given
        LoginUserRequest mockLoginUserRequest = new LoginUserRequest();
        mockLoginUserRequest.setEmail("test-email");
        mockLoginUserRequest.setPassword("test-password");

        User mockUser = new User();
        mockUser.setEmail("test-email");
        mockUser.setPassword("test-password");

        Authentication mockAuthentication = mock(Authentication.class);

        UserDetailsImpl mockUserDetails = new UserDetailsImpl();
        mockUserDetails.setId(1L);
        mockUserDetails.setName("test-name");
        mockUserDetails.setSurname("test-surname");
        mockUserDetails.setEmail("test-email");
        mockUserDetails.setAuthorities(Collections.of(new SimpleGrantedAuthority("ROLE_VICTIM")));

        JwtResponse mockJwtResponse = new JwtResponse("test-jwt-token", 1L, "test-name", "test-surname", "test-email", List.of("ROLE_VICTIM"));

        // when
        when(authenticationManager.authenticate(any())).thenReturn(mockAuthentication);
        when(jwtUtils.generateJwtToken(any())).thenReturn("test-jwt-token");
        when(mockAuthentication.getPrincipal()).thenReturn(mockUserDetails);

        // then
        ResponseEntity<JwtResponse> result = authService.signin(mockLoginUserRequest);
        assertEquals(mockJwtResponse, result.getBody());
    }
}