package com.ccoins.coins.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_match", referencedColumnName = "id")
    private Match match;

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="voting")
    @JsonIgnoreProperties({"voting"})
    @JsonManagedReference
    private List<Song> songs;

}
