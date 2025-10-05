package com.digi.auth.domain.model.dto;

import jakarta.validation.constraints.Email;

public class CustomerUpdateDto {
    // All optional; update only provided fields
    private String fullName;

    @Email
    private String email;

    private String phone;

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
