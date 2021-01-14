package com.sample.evite.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class EventResponseDto {
    private Long eventId;
    private String name;
    private String location;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private String startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private String endTime;
}
