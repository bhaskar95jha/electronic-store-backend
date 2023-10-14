package com.bhaskar.store.management.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tests")
public class HomeController {

    @GetMapping
    public String just(){
        return "Hi all i have started my new journey";
    }
}
