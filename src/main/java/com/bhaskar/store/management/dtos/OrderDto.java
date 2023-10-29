package com.bhaskar.store.management.dtos;
import com.sun.org.apache.bcel.internal.generic.NEW;
import lombok.*;

import javax.validation.constraints.NotBlank;
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
    @NotBlank(message = "billing address should not be blank")
    private String billingAddress;
    @NotBlank(message = "billing phone should not be blank")
    private String billingPhone;
    @NotBlank(message = "billing name should not be blank")
    private String billingName;
    private Date orderDate;
    private Date deliveredDate;
    private List<OrderItemDto> orderItems = new ArrayList<>();
}
