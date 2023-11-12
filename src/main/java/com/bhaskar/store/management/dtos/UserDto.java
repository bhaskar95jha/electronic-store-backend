package com.bhaskar.store.management.dtos;


import com.bhaskar.store.management.entity.Role;
import com.bhaskar.store.management.utility.ImageNameValidator;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String userId;

    @Size(min = 2, max = 15,message = "Invalid name it should be min 2 char and max 15 char!!")
    private String name;

    //@Email(message = "Invalid email !!")
    @Pattern(regexp="^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$" ,message = "Invalid email !!")
    private String email;

    @Size(min = 5,max = 16,message = "Invalid password !!")
    private String password;

    @NotBlank(message = "Gender is required")
    private String gender;

    @Size(min = 10, max = 100, message = "Invalid about info")
    private String about;

    @ImageNameValidator
    private String imageName;

    private Set<RoleDTO> roles = new HashSet<>();
}
