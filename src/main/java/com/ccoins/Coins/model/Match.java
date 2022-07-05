package com.ccoins.Coins.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Match {
    private Long id;
    private String code;
    private Long points;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean active;
    private Long game;
}
