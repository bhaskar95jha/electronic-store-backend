package com.bhaskar.store.management.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {
    private int cartItemId;
    private ProductDto productDto;
    private int quantity;
    private int totqlPrice;
}
