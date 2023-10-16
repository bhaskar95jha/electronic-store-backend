package com.bhaskar.store.management.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Category {

    @Id
    private String categoryId;
    private String title;
    private String description;
    private String categoryImage;
}
