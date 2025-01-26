package com.elearn.app.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@PreAuthorize("hasRole('ADMIN')")
@RestController //Created to test method level security
public class TestController {

    @PostMapping("/test")
//    @PreAuthorize("hasRole('ADMIN')")
    public  String testing(){
        return "testing";
    }

    @GetMapping("/test")
//    @PreAuthorize("hasRole('GUEST')")
    public  String test(){
        return "testing";
    }

    @GetMapping("/all")
    public String test1(){
        return "Open api endpoint";
    }
}
