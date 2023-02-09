package com.ccoins.coins.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.ccoins.coins.utils.DateUtils.AUTO_DATE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Matches")
@Table(name = "MATCHES")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="start_date",insertable = false, updatable = false,
            columnDefinition = AUTO_DATE)
    private LocalDateTime startDate;

    @Column(name="end_date")
    private LocalDateTime endDate;

    @Column(name="active", columnDefinition = "boolean default true")
    private boolean active;

    @Column(name="fk_game")
    private Long game;
}
