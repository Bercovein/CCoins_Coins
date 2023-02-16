package com.ccoins.coins.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.ccoins.coins.utils.DateUtils.AUTO_DATE;

@Getter
@Setter
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

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "fk_song")
    @JsonIgnoreProperties({"votes"})
    @JsonBackReference
    private Song song;

    @Column(name = "fk_client")
    private Long client;
}
