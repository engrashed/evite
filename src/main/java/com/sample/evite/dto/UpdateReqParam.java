package com.sample.evite.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateReqParam {
    @NotNull
    private Long eventId;

    private String name;

    private String location;

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm")
    private String startTime;

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm")
    private String endTime;
}
