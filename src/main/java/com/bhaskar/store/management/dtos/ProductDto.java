package com.bhaskar.store.management.dtos;

import com.bhaskar.store.management.entity.Category;
import lombok.*;

import javax.validation.constraints.Size;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class ProductDto {

    private String productId;

    @Size(min = 3, max = 100, message = "Title should be min 3 char and maximum 100 char")
    private String title;
    @Size(min = 5, max = 400, message = "Description should be min 3 char and maximum 400 char")
    private String description;
    private int mrp;
    private int sellPrice;
    private int quantity;
    private String color;
    private Date addedDate;
    private boolean stock ;
    private boolean live;
    private String productImage;
    private CategoryDto category;
}
