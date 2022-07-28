package com.ccoins.coins.dto;

import com.ccoins.coins.model.Match;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.ccoins.coins.utils.DateUtils.AUTO_DATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoinsDTO {

    private Long id;
    private LocalDateTime dateTime;
    private Long quantity;
    private boolean active;
    private Long client;
    private Long party;
}
