package com.ccoins.coins.model;

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
@Entity(name = "Coins")
@Table(name = "COINS")
public class Coins {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="start_date",insertable = false, updatable = false,
            columnDefinition = AUTO_DATE)
    private LocalDateTime dateTime;

    @Column(name="quantity")
    private Long quantity;

    @Column(name="active", columnDefinition = "boolean default true")
    private boolean active;

    @Column(name="fk_client_party")
    private Long clientParty;

    @Column(name="fk_prize")
    private Long prize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_match", referencedColumnName = "id")
    private Match match;
}
