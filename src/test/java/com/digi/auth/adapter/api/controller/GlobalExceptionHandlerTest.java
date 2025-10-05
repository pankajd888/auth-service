package com.digi.auth.adapter.api.controller;

import com.digi.auth.domain.model.dto.ErrorResponseDto;
import com.digi.auth.domain.model.dto.ValidationErrorResponseDto;
import com.digi.auth.exception.BadRequestException;
import com.digi.auth.exception.NotFoundException;
import com.digi.auth.exception.UnauthorizedException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;


import static org.junit.jupiter.api.Assertions.*;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleBadRequestException_returns400AndMessage() {
        BadRequestException ex = new BadRequestException("Invalid input");
        ResponseEntity<ErrorResponseDto> response = handler.handleBadRequestException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Invalid input", response.getBody().getError());
    }

    @Test
    void handleNotFoundException_returns404AndMessage() {
        NotFoundException ex = new NotFoundException("User not found");
        ResponseEntity<ErrorResponseDto> response = handler.handleNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("User not found", response.getBody().getError());
    }

    @Test
    void handleMethodArgumentNotValid_returns400AndAggregatedDetails() throws NoSuchMethodException {
        Object targetObject = new Object();
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(targetObject, "target");
        bindingResult.addError(new FieldError("target", "username", "must not be blank"));
        bindingResult.addError(new FieldError("target", "password", "size must be between 8 and 64"));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<ValidationErrorResponseDto> response = handler.handleMethodArgumentNotValid(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Validation failed", response.getBody().getError());
        assertNotNull(response.getBody().getDetails());
        assertEquals(2, response.getBody().getDetails().size());
        assertTrue(response.getBody().getDetails().contains("username: must not be blank"));
        assertTrue(response.getBody().getDetails().contains("password: size must be between 8 and 64"));
    }

    @Test
    void handleUnauthorizedException_returns401AndUnauthorizedMessage() {
        UnauthorizedException ex = new UnauthorizedException("Some message");
        ResponseEntity<java.util.Map<String, String>> response = handler.handleUnauthorizedException(ex);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Unauthorized", response.getBody().get("error"));
    }
}


