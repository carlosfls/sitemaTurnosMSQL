package com.sistemacitas.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/error")
    public String showErrorPage(Exception e,Model modelo){
        modelo.addAttribute("errorMessage",e.getMessage());
        return "error";
    }
}
