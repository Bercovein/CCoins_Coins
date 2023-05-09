package com.ccoins.coins.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodeDTO {

    private Long id;
    private Long matchId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String code;
    private Long points;
    private Long prize;
    private boolean perPerson;
    private boolean oneUse;
    private boolean active;
    private String state;
    private boolean closeable;
    private boolean editable;
    private Long game;

}
