package com.ccoins.coins.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoinsDTO {

    private Long id;
    private LocalDateTime dateTime;
    private Long quantity;
    private boolean active;
    private Long clientParty;
    private String state;
    private Long prize;
    private boolean updatable;

}
