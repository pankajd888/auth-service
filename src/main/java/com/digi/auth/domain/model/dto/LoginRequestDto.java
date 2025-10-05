package com.digi.auth.domain.model.dto;

import jakarta.validation.constraints.NotBlank;
import com.digi.auth.validation.ValidUsername;
import com.digi.auth.validation.ValidPassword;

public class LoginRequestDto {
    @ValidUsername
    private String username;

    @NotBlank
    @ValidPassword
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


