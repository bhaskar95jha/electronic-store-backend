package com.bhaskar.store.management.dtos;

import com.bhaskar.store.management.utility.ImageNameValidator;
import lombok.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

    private String categoryId;
    @Size(min = 2, max = 15,message = "Invalid title it should be min 2 char and max 15 char!!")
    private String title;
    @Size(min = 5, max = 100,message = "Invalid description it should be min 2 char and max 15 char!!")
    private String description;
    @ImageNameValidator
    private String categoryImage;
}
