package com.example.demo.user.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class LoginController {
    @GetMapping("/login.html")
    public String loginPage(){

        return "/login.html";
    }

    @GetMapping("/index.html")
    public String index(){
        return "/index.html";
    }

    @GetMapping("/")
    public String basePage(){
        return "/index.html";
    }

    @GetMapping("/error.html")
    public String error(){
        return "/error.html";
    }


}
