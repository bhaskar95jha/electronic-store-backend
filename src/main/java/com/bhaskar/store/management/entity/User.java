package com.bhaskar.store.management.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Order> order = new ArrayList<>();

}
