package com.sample.evite.response;

import lombok.Data;

import java.util.List;

@Data
public class UserResponseListDto {
    List<UserResponseDto> userList;
}
