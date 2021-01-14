package com.sample.evite.response;

import lombok.Data;

import java.util.List;

@Data
public class EventResponseListDto {
    private List<EventResponseDto> eventList;
}
