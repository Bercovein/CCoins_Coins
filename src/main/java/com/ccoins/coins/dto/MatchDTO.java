package com.ccoins.coins.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchDTO {

    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean active;
    private Long game;
}
