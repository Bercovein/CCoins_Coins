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
@Entity(name = "votes")
@Table(name = "votes")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="start_date",insertable = false, updatable = false,
            columnDefinition = AUTO_DATE)
    private LocalDateTime dateTime;

    @OneToOne
    @JoinColumn(name = "fk_song")
    private Song song;

    @Column(name = "fk_client")
    private Long client;
}
