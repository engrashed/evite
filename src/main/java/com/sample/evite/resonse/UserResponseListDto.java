package com.sample.evite.resonse;

import lombok.Data;

import java.util.List;

@Data
public class UserResponseListDto {
    List<UserResponseDto> userList;
}
