package com.sample.evite.resonse;

import lombok.Data;

@Data
public class UserResponseDto {
    private Long userId;
    private String name;
    private String email;
    private String createdAt;
}
