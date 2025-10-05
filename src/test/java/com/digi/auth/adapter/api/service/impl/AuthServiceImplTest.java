package com.digi.auth.adapter.api.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.digi.auth.adapter.repository.UserRepository;
import com.digi.auth.domain.model.dto.LoginRequestDto;
import com.digi.auth.domain.model.dto.LoginResponseDto;
import com.digi.auth.domain.model.entity.UserEntity;
import com.digi.auth.exception.BadRequestException;
import com.digi.auth.security.InMemoryTokenStore;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private InMemoryTokenStore tokenStore;

    @InjectMocks
    private AuthServiceImpl authService;

    private LoginRequestDto request;

    @BeforeEach
    void setup() {
        request = new LoginRequestDto();
        request.setUsername("john");
        request.setPassword("secret");
    }

    @Test
    void login_success_returnsToken() {
        UserEntity user = new UserEntity();
        user.setUsername("john");
        user.setPassword("secret");
        user.setStatus("ACT");
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));
        when(tokenStore.issueToken("john")).thenReturn("test-token");

        LoginResponseDto resp = authService.login(request);

        assertEquals(true, resp.isSuccess());
        assertEquals("Login successful", resp.getMessage());
        assertNotNull(resp.getToken());
    }

    @Test
    void login_userNotFound_throwsBadRequest() {
        when(userRepository.findByUsername("john")).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> authService.login(request));
    }

    @Test
    void login_passwordMismatch_throwsBadRequest() {
        UserEntity user = new UserEntity();
        user.setUsername("john");
        user.setPassword("different");
        user.setStatus("ACT");
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));

        assertThrows(BadRequestException.class, () -> authService.login(request));
    }

    @Test
    void login_inactive_throwsBadRequest() {
        UserEntity user = new UserEntity();
        user.setUsername("john");
        user.setPassword("secret");
        user.setStatus("DEL");
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));

        assertThrows(BadRequestException.class, () -> authService.login(request));
    }
}


