package com.digi.auth.adapter.api.service.impl;

import org.springframework.stereotype.Service;

import com.digi.auth.adapter.api.service.AuthService;
import com.digi.auth.adapter.repository.UserRepository;
import com.digi.auth.domain.model.dto.LoginRequestDto;
import com.digi.auth.domain.model.dto.LoginResponseDto;
import com.digi.auth.domain.model.entity.UserEntity;
import com.digi.auth.exception.BadRequestException;
import com.digi.auth.security.InMemoryTokenStore;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final InMemoryTokenStore tokenStore;

    public AuthServiceImpl(UserRepository userRepository, InMemoryTokenStore tokenStore) {
        this.userRepository = userRepository;
        this.tokenStore = tokenStore;
    }

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        UserEntity user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadRequestException("Invalid username or password"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new BadRequestException("Invalid username or password");
        }

        if (!"ACT".equals(user.getStatus())) {
            throw new BadRequestException("Account is not active");
        }

        LoginResponseDto response = new LoginResponseDto();
        response.setSuccess(true);
        response.setMessage("Login successful");
        String token = tokenStore.issueToken(user.getUsername());
        response.setToken(token);
        return response;
    }
}


