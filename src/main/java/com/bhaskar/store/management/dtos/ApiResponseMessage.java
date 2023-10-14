package com.bhaskar.store.management.dtos;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponseMessage {
    private String message;
    private boolean isSuccess;
    private HttpStatus status;
}
