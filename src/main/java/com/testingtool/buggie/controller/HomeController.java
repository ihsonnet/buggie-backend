package com.testingtool.buggie.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor
@RestController
public class HomeController {
    @GetMapping("/")
    public String  home(){
        return "Welcome Buggie Homepage!";
    }
}
