package com.ccoins.coins.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "votations")
@Table(name = "votations")
public class Voting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_winner_song", referencedColumnName = "id")
    @JsonIgnoreProperties("voting")
    private Song winnerSong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_match", referencedColumnName = "id")
    private Match match;

    @OneToMany(mappedBy="voting")
    private List<Song> songs;

}
