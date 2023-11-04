package com.bhaskar.store.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class StoreManagementApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(StoreManagementApplication.class, args);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args)throws Exception{
        System.out.println(passwordEncoder.encode("kapil@123"));
    }

}
