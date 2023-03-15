package com.ccoins.coins.repository;


import com.ccoins.coins.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IVoteRepository extends JpaRepository<Vote, Long> {

    @Query(value = "SELECT COUNT(v.id) FROM votes v " +
            " WHERE v.fk_song = :id", nativeQuery = true)
    Integer countBySongId(@Param("id") Long id);

    Optional<List<Vote>> getByClientAndSongVotingId(Long client, Long id);

    @Query(value = "SELECT v.fk_client from votes v " +
            " where v.fk_song = :songId group by v.fk_client", nativeQuery = true)
    List<Long> findClientIdByVotedSong(@Param("songId") Long songId);

    @Query(value = "SELECT IF(EXISTS(SELECT s.ID FROM votes v " +
            "inner join clients c on c.ID = v.FK_CLIENT " +
            "inner join songs s on s.ID = v.FK_SONG " +
            "inner join votations vo on vo.ID = s.FK_VOTATION " +
            "inner join matches m on m.ID = vo.FK_MATCH " +
            "inner join games g on g.ID = m.FK_GAME " +
            "inner join bars b on b.ID = g.FK_BAR " +
            "where c.ip = :userIp " +
            "and b.ID = :barId " +
            "and vo.FK_WINNER_SONG IS NULL) = 1, 'true', 'false') as Boolean",nativeQuery = true)
    boolean hasVotedAlready(@Param("userIp") String userIp, @Param("barId") Long barId);
}
