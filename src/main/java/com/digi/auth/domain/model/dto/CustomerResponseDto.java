package com.digi.auth.domain.model.dto;

public class CustomerResponseDto {
    private String username;
    private String fullName;
    private String email;
    private String phone;

    public CustomerResponseDto() {} // no-args for Jackson

    // REQUIRED: HQL constructor (order & types must match the query)
    public CustomerResponseDto(String username, String fullName, String email, String phone) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}

