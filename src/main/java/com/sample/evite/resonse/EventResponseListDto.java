package com.sample.evite.resonse;

import lombok.Data;

import java.util.List;

@Data
public class EventResponseListDto {
    private List<EventResponseDto> eventList;
}
