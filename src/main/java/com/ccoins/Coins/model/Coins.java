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
public class Coins {
    private Long id;
    private LocalDateTime dateTime;
    private Long quantity;
    private boolean active;
    private Long client;
    private Activity activity;
}
