package com.bhaskar.store.management.services.impl;

import com.bhaskar.store.management.controllers.UserController;
import com.bhaskar.store.management.dtos.PageableResponse;
import com.bhaskar.store.management.dtos.UserDto;
import com.bhaskar.store.management.exceptions.ResourceNotFoundException;
import com.bhaskar.store.management.entity.User;
import com.bhaskar.store.management.repositories.UserRepo;
import com.bhaskar.store.management.services.UserService;
import com.bhaskar.store.management.utility.Util;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${user.profile.image.path}")
    private String imagePath;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger log = LoggerFactory.getLogger(UserController.class);
    @Override
    public UserDto createUser(UserDto userDto) {
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
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
        //delete user profile image
        String imageFullPathAndName = imagePath+ File.separator+user.getImageName();
        try{
            Path path = Paths.get(imageFullPathAndName);
            Files.delete(path);
        }catch (NoSuchFileException ex){
            log.info("User image not found in folder");
            ex.printStackTrace();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

        userRepo.delete(user);
        //userRepo.deleteById(userId);
    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {
        //Sort sort = Sort.by(sortBy);
        Sort sort = sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<User> pages = userRepo.findAll(pageable);

        PageableResponse<UserDto> pageableResponse = Util.getPageableResponse(pages, UserDto.class);

//        List<User> users = pages.getContent();
//        List<UserDto> dtoList = users.stream().map((user) -> modelMapper.map(user,UserDto.class)).collect(Collectors.toList());
//        PageableResponse<UserDto> response = new PageableResponse<>();
//        response.setContent(dtoList);
//        response.setPageNumber(pages.getNumber());
//        response.setPageSize(pages.getSize());
//        response.setTotalElements(pages.getTotalElements());
//        response.setTotalPages(pages.getTotalPages());
//        response.setLastPage(pages.isLast());

        //by using builder pattern
//        PageableResponse<UserDto> response = PageableResponse
//                .builder()
//                .content(Collections.singletonList((dtoList)))
//                .pageNumber(pages.getNumber())
//                .pageSize(pages.getSize())
//                .totalElements(pages.getTotalElements())
//                .totalPages(pages.getTotalPages())
//                .lastPage(pages.isLast())
//                .build();

        return pageableResponse;
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
