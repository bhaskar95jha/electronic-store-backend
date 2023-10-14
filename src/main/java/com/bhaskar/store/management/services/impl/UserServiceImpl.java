package com.bhaskar.store.management.services.impl;

import com.bhaskar.store.management.dtos.UserDto;
import com.bhaskar.store.management.exceptions.ResourceNotFoundException;
import com.bhaskar.store.management.models.User;
import com.bhaskar.store.management.repositories.UserRepo;
import com.bhaskar.store.management.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);
        User user = modelMapper.map(userDto,User.class);
        User savedUser = userRepo.save(user);
        UserDto savedUserDto = modelMapper.map(savedUser,UserDto.class);
        return savedUserDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User doesn't found with given id"+userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());
        user.setGender(userDto.getGender());
        userDto.setAbout(userDto.getAbout());
        User updatedUser = userRepo.save(user);
        UserDto updatedUserDto = modelMapper.map(updatedUser,UserDto.class);
        return updatedUserDto;
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User doesn't found with given id"+userId));
        userRepo.delete(user);
        //userRepo.deleteById(userId);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> users = userRepo.findAll();
        List<UserDto> dtoList = users.stream().map((user) -> modelMapper.map(user,UserDto.class)).collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User doesn't found with given id"+userId));
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User doesn't found with given email"+email));
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public List<UserDto> searchUser(String keyWord) {
        List<User> users = userRepo.findByNameContaining(keyWord).orElseThrow(() -> new ResourceNotFoundException("User doesn't found with given email"+keyWord));
        List<UserDto> userDtoList = users.stream().map((user) -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        return userDtoList;
    }

    @Override
    public List<UserDto> searchByGender(String gender) {
        List<User> users = userRepo.findByGenderContaining(gender).orElseThrow(() -> new ResourceNotFoundException("User doesn't found with given email"+gender));
        List<UserDto> userDtoList = users.stream().map((user) -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        return userDtoList;
    }

}
