package com.ccoins.coins.repository;


import com.ccoins.coins.model.CoinsReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICoinsReportRepository extends JpaRepository<CoinsReport, Long> {

    @Query(value = "select COINS_ID, DATE, COINS, ACTIVITY, CLIENT " +
            " from (SELECT c.id AS COINS_ID, c.START_DATE AS DATE, c.QUANTITY AS COINS, pr.NAME AS ACTIVITY, cl.NICK_NAME AS CLIENT " +
            " FROM coins c" +
            " inner join clients_parties cp on cp.FK_CLIENT = c.FK_CLIENT_PARTY" +
            " inner join clients cl on cl.ID = cp.FK_CLIENT" +
            " inner join prizes pr on pr.ID = c.FK_PRIZE" +
            " where cp.FK_PARTY = :partyId" +
            " union    " +
            " SELECT c.id AS COINS_ID, c.START_DATE AS DATE, c.QUANTITY AS COINS, g.NAME AS ACTIVITY, cl.NICK_NAME AS CLIENT FROM coins c" +
            " inner join clients_parties cp on cp.FK_CLIENT = c.FK_CLIENT_PARTY" +
            " inner join matches m on m.ID = c.FK_MATCH" +
            " inner join games g on g.ID = m.FK_GAME" +
            " inner join clients cl on cl.ID = cp.FK_CLIENT" +
            " where cp.FK_PARTY = :partyId) AS COINS_REPORT" +
            " order by DATE desc", nativeQuery = true)
    List<CoinsReport> getAllCoinsFromParty(@Param("partyId") Long partyId);
    
}
