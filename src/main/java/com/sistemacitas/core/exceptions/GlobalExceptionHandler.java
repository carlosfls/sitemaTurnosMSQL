package com.sistemacitas.core.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleExceptions(Exception e,RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("errMessage",e.getMessage());
        return "redirect:/error";
    }
}
