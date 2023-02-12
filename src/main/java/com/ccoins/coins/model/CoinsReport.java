package com.ccoins.coins.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "CoinsReport")
@Table(name = "COINS_REPORT")
public class CoinsReport {

    @Id
    @Column(name="COINS_ID")
    private Long coinsId;

    @Column(name="DATE")
    private String date;

    @Column(name="COINS")
    private Long coins;

    @Column(name="ACTIVITY")
    private String activity;

    @Column(name="CLIENT")
    private String client;

}
