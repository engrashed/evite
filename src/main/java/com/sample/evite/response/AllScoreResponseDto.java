package com.sample.evite.response;

import lombok.Data;

import java.util.List;

@Data
public class AllScoreResponseDto {
    private List<PlayerHistoryResponseDto> allScore;
}
