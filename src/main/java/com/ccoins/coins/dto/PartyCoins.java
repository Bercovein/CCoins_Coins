package com.ccoins.coins.dto;

import com.ccoins.coins.model.Match;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static com.ccoins.coins.utils.DateUtils.AUTO_DATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PartyCoins {

    private Long partyId;

    private List<CoinsDTO> coins;

    private Long quantity;
}
