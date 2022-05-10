package com.ccoins.Coins.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Code extends Activity{
    private Long id;
    private String code;

    @Builder
    public Code(Long id, String name, Long points, LocalDateTime startDate, LocalDateTime endDate, boolean active, Long bar, Long id1, String code) {
        super(id, name, points, startDate, endDate, active, bar);
        this.id = id1;
        this.code = code;
    }
}
