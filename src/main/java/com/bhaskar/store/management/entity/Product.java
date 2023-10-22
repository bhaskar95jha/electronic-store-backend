package com.bhaskar.store.management.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    private String productId;

    private String title;
    private String description;
    private int mrp;
    private int sellPrice;
    private int quantity;
    private String color;
    private Date addedDate;
    private boolean stock ;
    private boolean live;
    private String productImage;

}
