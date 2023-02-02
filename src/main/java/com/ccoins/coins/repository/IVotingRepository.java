package com.ccoins.coins.repository;


import com.ccoins.coins.model.Voting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IVotingRepository extends JpaRepository<Voting, Long> {

    @Query(value = "select * from votations v " +
            "inner join songs s on s.FK_VOTATION = v.id " +
            "inner join matches m on m.ID = v.FK_MATCH " +
            "inner join games g on g.ID = m.FK_GAME " +
            "inner join games_types gt on gt.id = g.FK_GAME_TYPE " +
            "inner join bars b on b.ID = g.FK_BAR " +
            "where b.id = :id " +
            "and v.FK_WINNER_SONG is null", nativeQuery = true)
    Optional<Voting> getByBarAndWinnerSongIsNull(@Param("id") Long id);
}