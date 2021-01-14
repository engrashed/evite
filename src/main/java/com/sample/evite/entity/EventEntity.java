package com.sample.evite.entity;

import lombok.Data;

@Data
public class EventEntity {
    private Long eventId;
    private String name;
    private String location;
    private String timeZone;
    private String startTime;
    private String endTime;
    private String createdAt;
    private boolean deleteFlg;
}
