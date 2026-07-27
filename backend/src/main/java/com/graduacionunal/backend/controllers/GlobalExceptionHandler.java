package com.graduacionunal.backend.controllers;

import com.graduacionunal.backend.datastructures.InvalidPrerequisiteReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidPrerequisiteReferenceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleInvalidPrerequisiteReference(InvalidPrerequisiteReferenceException exception) {
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", exception.getMessage());
        response.put("referenciasInvalidas", exception.getInvalidReferences());
        return response;
    }
}
