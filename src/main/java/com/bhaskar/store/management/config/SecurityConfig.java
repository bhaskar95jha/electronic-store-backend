package com.bhaskar.store.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(){

        //user create
        UserDetails adminUser = User
                .builder()
                .username("bhaskar")
                .password(passwordEncoder().encode("bhaskarjha"))
                .roles("ADMIN")
                .build();

        UserDetails normalUser = User.builder()
                .username("sachin")
                .password(passwordEncoder().encode("sachinjha"))
                .roles("NORMAL")
                .build();

        List<UserDetails> users = new ArrayList<>();
        users.add(normalUser);
        users.add(adminUser);

        //  InMemoryUserDetailsManager - is the implementation class of UserDetailService
        return new InMemoryUserDetailsManager(users);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
