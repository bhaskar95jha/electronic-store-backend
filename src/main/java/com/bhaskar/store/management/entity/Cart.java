package com.bhaskar.store.management.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Cart {
    @Id
    private String cartId;

    private Date createdDate;
    @OneToOne
    private User user;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CartItem> items = new ArrayList<>();

}
