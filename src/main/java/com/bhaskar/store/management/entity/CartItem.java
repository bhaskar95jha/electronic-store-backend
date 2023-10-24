package com.bhaskar.store.management.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "cart_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartItemId;
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private int quantity;
    private int totqlPrice;

   // private Cart cart;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;
}
