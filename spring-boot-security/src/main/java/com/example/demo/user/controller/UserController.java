package com.example.demo.user.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

//@Controller
@RestController
public class UserController {

    @GetMapping("/principal")
    public String principal(){
        return SecurityContextHolder.getContext().getAuthentication().toString();
    }
    //@PreAuthorize(value = "hasRole('ROOT')")
    //@RolesAllowed(value = {"ROOT"})
    @Secured(value = {"ROLE_ROOT"})
    @GetMapping("/query")
    public String query(){
        return "测试查询";
    }

   // @PreAuthorize(value = "hasRole('admin')")
    //@RolesAllowed(value = {"admin"})
   @Secured(value = {"admin"})
    @GetMapping("/save")
    public String save(){
        return "添加 ";
    }
}
