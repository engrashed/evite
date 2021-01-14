package com.sample.evite.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateReqParam {
    @NotNull
    private String name;
    @NotNull
    private String location;
    @NotNull
    private String timeZone;
    @NotNull
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm")
    private String startTime;
    @NotNull
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm")
    private String endTime;
}
