package com.sample.evite.entity;

import lombok.Data;

@Data
public class UserEntity {
    private Long userId;
    private String name;
    private String email;
    private String password;
    private String createdAt;
    private String deleteFlg;
}
