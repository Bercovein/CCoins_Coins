package com.ccoins.coins.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Votation {
    private Long id;
    private Song winnerSong;
    private Match match;
}
