package com.bhaskar.store.management.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ItemToCart {
    private String productId;
    private int quantity;
}
