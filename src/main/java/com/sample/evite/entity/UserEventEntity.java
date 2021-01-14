package com.sample.evite.entity;

import lombok.Data;

@Data
public class UserEventEntity {
    private Long userEventId;
    private String email;
    private Long eventId;
}
