package com.digi.auth.adapter.api.service;

import com.digi.auth.domain.model.dto.LoginRequestDto;
import com.digi.auth.domain.model.dto.LoginResponseDto;

public interface AuthService {

    LoginResponseDto login(LoginRequestDto request);
}


