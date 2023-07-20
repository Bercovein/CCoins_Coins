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
@Entity(name = "CoinsReportStates")
@Table(name = "COINS_REPORT_STATES")
public class CoinsReportStates {

    @Id
    @Column(name="ID")
    private Long coinsId;

    @Column(name="START_DATE")
    private String date;

    @Column(name="TABLE_NUMBER")
    private Long tableNumber;

    @Column(name="NAME")
    private String prizeName;

    @Column(name="STATE")
    private String state;

    @Column(name="PARTY_NAME")
    private String partyName;

    @Column(name="UPDATABLE")
    private Boolean updatable;
}
