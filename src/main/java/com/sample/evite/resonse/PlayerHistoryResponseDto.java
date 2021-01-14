package com.sample.evite.resonse;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PlayerHistoryResponseDto {
    @JsonProperty("player")
    private String name;
    private Integer topScore;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String topScoreTime;
    private Integer lowScore;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String lowScoreTime;
    private Integer averageScore;
    private List<Integer> allScores;
}
