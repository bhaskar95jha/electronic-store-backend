package com.bhaskar.store.management.dtos;

import com.bhaskar.store.management.entity.CartItem;
import com.bhaskar.store.management.entity.User;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDto {
    private String cartId;
    private Date createdDate;
    private UserDto user;
    private List<CartItemDto> items = new ArrayList<>();
}
