package com.ccoins.Coins.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vote {
    private Long id;
    private LocalDateTime dateTime;
    private Song song;
    private Long client;
}
