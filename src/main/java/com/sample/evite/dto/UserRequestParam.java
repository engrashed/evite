package com.sample.evite.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserRequestParam {
    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String password;

}
