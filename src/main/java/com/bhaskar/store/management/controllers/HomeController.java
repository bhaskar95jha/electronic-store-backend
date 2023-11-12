package com.bhaskar.store.management.controllers;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tests")
@Api(value = "HomeController" , description = "This Rest API is for testing purpose !!")
public class HomeController {

    @GetMapping
    public String just(){
        return "Hi all i have started my new journey";
    }
}
