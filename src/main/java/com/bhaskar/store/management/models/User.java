package com.bhaskar.store.management.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    private String userId;
    private String name;

    @Column(unique = true)
    private String email;

    private String password;
    private String gender;
    private String about;
    private String imageName;


}
