package com.bhaskar.store.management.dtos;

import com.bhaskar.store.management.entity.Product;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductQuantity {
    private Product product;
    private int quantity;
}
