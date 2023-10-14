package com.bhaskar.store.management.services;

import com.bhaskar.store.management.dtos.UserDto;

import java.util.List;

public interface UserService {

    //create user
    UserDto createUser(UserDto userDto);

    //update user
    UserDto updateUser(UserDto userDto, String userId);

    //delete user
    void deleteUser(String userId);

    //get all user
    List<UserDto> getAllUser();

    //get user by id
    UserDto getUserById(String userId);

    //get user by email
    UserDto getUserByEmail(String email);

    //search user
    List<UserDto> searchUser(String keyWord);

    List<UserDto> searchByGender(String gender);
}
