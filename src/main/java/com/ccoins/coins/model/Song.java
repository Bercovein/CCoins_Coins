package com.ccoins.coins.model;

import com.ccoins.coins.dto.SongDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Voting voting;

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="song")
    @JsonManagedReference
    private List<Vote> votes;

    public static Song convert (SongDTO dto){

        if(dto == null)
            return null;

        return Song.builder()
                .id(dto.getId())
                .name(dto.getName())
                .uri(dto.getUri())
                .build();
    }
}
