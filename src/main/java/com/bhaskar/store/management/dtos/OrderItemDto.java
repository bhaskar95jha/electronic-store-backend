package com.bhaskar.store.management.dtos;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {
    private String orderItemId;
    private int quantity;
    private int totalPrice;
    private ProductDto product;
}
