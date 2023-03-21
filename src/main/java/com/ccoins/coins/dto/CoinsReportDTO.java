package com.ccoins.coins.dto;

import com.ccoins.coins.model.CoinsReport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoinsReportDTO {

    private Long totalCoins;
    private Page<CoinsReport> report;

}
