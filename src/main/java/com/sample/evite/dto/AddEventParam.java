package com.sample.evite.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddEventParam {
    @NotNull
    private long eventId;

    @NotNull
    private String email;
}
