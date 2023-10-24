package com.bhaskar.store.management.dtos;

import com.bhaskar.store.management.entity.Product;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {
    private int cartItemId;
    private ProductDto product;
    private int quantity;
    private int totalPrice;
}
