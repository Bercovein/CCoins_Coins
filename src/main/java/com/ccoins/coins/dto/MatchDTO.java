package com.ccoins.coins.dto;

import com.ccoins.coins.model.Match;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchDTO {

    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean active;
    private Long game;

    public  static MatchDTO convert(Match match){
        return MatchDTO.builder()
                .id(match.getId())
                .startDate(match.getStartDate())
                .endDate(match.getEndDate())
                .active(match.isActive())
                .game(match.getGame())
                .build();
    }
}
