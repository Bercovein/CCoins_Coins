package com.ccoins.coins.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Entity(name = "songs")
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name = "uri")
    private String uri;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_votation")
    @JsonIgnoreProperties({"songs", "winnerSong"})
    @JsonBackReference
    private Voting voting;

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="song")
    @JsonManagedReference
    private List<Vote> votes;
}
