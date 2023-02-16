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
}
