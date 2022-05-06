package com.ccoins.Coins.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Song {
    private Long id;
    private String name;
    private Votation votation;
}
