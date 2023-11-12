package com.bhaskar.store.management.controllers;

import com.bhaskar.store.management.dtos.JwtRequest;
import com.bhaskar.store.management.dtos.JwtResponse;
import com.bhaskar.store.management.dtos.UserDto;
import com.bhaskar.store.management.exceptions.BadApiRequest;
import com.bhaskar.store.management.security.JwtHelper;
import com.bhaskar.store.management.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ModelMapper mapper;


    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtHelper jwtHelper;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request){
        this.doAuthenticate(request.getEmail(),request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.jwtHelper.generateToken(userDetails);
        UserDto userDto = mapper.map(userDetails,UserDto.class);

        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .userDto(userDto)
                .build();

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,password);
        try{
            manager.authenticate(authenticationToken);
        }catch (AuthenticationException ex){
            throw new BadApiRequest("Invalid Username or password !! {}");
        }
    }


    @GetMapping("/current")
    public ResponseEntity<UserDto> getCurrentUser(Principal principal){
        String name = principal.getName();
        UserDto userDto = mapper.map(userDetailsService.loadUserByUsername(name), UserDto.class);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }


}
