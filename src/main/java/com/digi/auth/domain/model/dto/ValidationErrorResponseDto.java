package com.digi.auth.domain.model.dto;

import java.util.List;

public class ValidationErrorResponseDto {

    private String error;
    private List<String> details;

    public ValidationErrorResponseDto() {
    }

    public ValidationErrorResponseDto(String error, List<String> details) {
        this.error = error;
        this.details = details;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }
}


