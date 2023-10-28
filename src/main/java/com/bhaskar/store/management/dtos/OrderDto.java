package com.bhaskar.store.management.dtos;
import com.sun.org.apache.bcel.internal.generic.NEW;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private String oredrId;
    private String orderStatus="PENDING";
    private String paymentStatus="NOTPAID";
    private int orderAmount;
    private String billingAddress;
    private String billingPhone;
    private String billingName;
    private Date orderDate= new Date();
    private Date deliveredDate;
    private List<OrderItemDto> orderItems = new ArrayList<>();
}
