package com.ccoins.coins.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class SpendCoinsRqDTO {

    @NotNull
    private Long clientParty;

    @NotNull
    private Long partyId;

    @NotNull
    private Long prizeId;

    @NotNull
    private Long prizePoints;
}
