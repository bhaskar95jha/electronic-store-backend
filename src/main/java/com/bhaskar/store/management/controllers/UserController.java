package com.bhaskar.store.management.controllers;

import com.bhaskar.store.management.dtos.ApiResponseMessage;
import com.bhaskar.store.management.dtos.UserDto;
import com.bhaskar.store.management.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    //create
    @PostMapping
    public ResponseEntity<UserDto>  createUser(@Valid @RequestBody UserDto userDto){
        UserDto createdUserDto = userService.createUser(userDto);
        return new ResponseEntity<>(createdUserDto,HttpStatus.CREATED);
    }

    //update
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto>  updateUser(@Valid @RequestBody UserDto userDto,
                                               @PathVariable("userId") String userId){
        UserDto updatedUser = userService.updateUser(userDto, userId);
        return new ResponseEntity<>(updatedUser,HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable("userId") String userId){
        userService.deleteUser(userId);
        ApiResponseMessage response = ApiResponseMessage
                .builder()
                .message("User Deleted Successfully")
                .isSuccess(Boolean.TRUE)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //get all
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "sortBy", required = false, defaultValue = "name") String sortBy,
            @RequestParam(value = "sortDir", required = false, defaultValue = "asc") String sortDir
    ){
        List<UserDto> users = userService.getAllUser(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    //get user by id
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUsersById(@PathVariable String userId){
        UserDto user = userService.getUserById(userId);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    //get user by email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUsersByEmail(@PathVariable String email){
        UserDto user = userService.getUserByEmail(email);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    //search user by name
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword){
        List<UserDto> users = userService.searchUser(keyword);
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    //get user by gender
    @GetMapping("/searchByGender/{gender}")
    public ResponseEntity<List<UserDto>> searchUserByGender(@PathVariable String gender){
        List<UserDto> users = userService.searchByGender(gender);
        return new ResponseEntity<>(users,HttpStatus.OK);
    }
}
