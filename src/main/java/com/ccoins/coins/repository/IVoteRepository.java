package com.ccoins.coins.repository;


import com.ccoins.coins.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IVoteRepository extends JpaRepository<Vote, Long> {

    Long countBySongId(@Param("id") Long id);

    Optional<List<Vote>> getByClientAndSongVotingId(Long client, Long id);
}
