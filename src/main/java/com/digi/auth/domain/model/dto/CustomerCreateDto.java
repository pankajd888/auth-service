package com.digi.auth.domain.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import com.digi.auth.validation.ValidUsername; // if you want to accept username in body; otherwise we'll ignore it

public class CustomerCreateDto {
    // Optional: keep username here if you prefer body to carry it. We'll actually override with actor for security.
    @ValidUsername
    private String username;

    @NotBlank
    private String fullName;

    @Email
    private String email;

    @NotBlank
    private String phone;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
