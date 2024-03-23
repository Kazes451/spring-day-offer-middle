package com.onedayoffer.taskdistribution.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice(annotations= RestController.class)
@Order(HIGHEST_PRECEDENCE)
@Slf4j
public class ControllerExceptionHandler {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(NoSuchElementException.class)
    private void handleNoSuchElementException(@NotNull HttpServletRequest request, @NotNull Exception e) {
        log.warn(
                "Bad request for {} {}: {}: {}",
                request.getMethod(), request.getRequestURI(), e.getClass().getSimpleName(), e.getMessage()
        );
    }

}
