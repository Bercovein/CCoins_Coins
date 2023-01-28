package com.ccoins.coins.repository;


import com.ccoins.coins.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IMatchRepository extends JpaRepository<Match, Long> {

    @Query(value = "select m.* from matches m " +
            "inner join games g on g.ID = m.FK_GAME " +
            "inner join games_types gt on gt.id = g.FK_GAME_TYPE " +
            "inner join bars b on b.ID = g.FK_BAR " +
            "where b.id = :id " +
            "and m.END_DATE is null " +
            "and g.ACTIVE is true " +
            "and gt.name = 'VOTE'", nativeQuery = true)
    Match getVotingMatchByBarId(@Param("id") Long id);

    Match getByGame(Long game);

    @Query(value = "select m.*, g.*, gt.*, b.* from matches m " +
            "inner join votations v on v.FK_MATCH = m.ID " +
            "inner join songs s on s.FK_VOTATION = v.ID " +
            "inner join games g on g.ID = m.FK_GAME  " +
            "inner join games_types gt on gt.id = g.FK_GAME_TYPE  " +
            "inner join bars b on b.ID = g.FK_BAR " +
            "where s.ID = :songId", nativeQuery = true)
    Match getMatchBySongId(@Param("songId")Long songId);
}
