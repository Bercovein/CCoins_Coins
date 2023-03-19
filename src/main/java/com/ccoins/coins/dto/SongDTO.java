package com.ccoins.coins.dto;

import com.ccoins.coins.model.Song;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SongDTO {

    private Long id;
    private String name;
    private Long votes;
    private String uri;

    public static SongDTO convert(Song song){
        if(song == null) return null;

        return SongDTO.builder()
                .id(song.getId())
                .uri(song.getUri())
                .name(song.getName())
                .votes((long) song.getVotes().size())
                .build();
    }
}
