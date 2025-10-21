package com.miniproject;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handles all exceptions
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error"; // Thymeleaf template
    }

    @ExceptionHandler(NullPointerException.class)
    public String handleNullPointer(Model model) {
        model.addAttribute("errorMessage", "A null pointer occurred!");
        return "error";
    }
}