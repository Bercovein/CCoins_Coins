package com.ccoins.coins.repository;


import com.ccoins.coins.model.CoinsReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ccoins.coins.repository.query.CoinsReport.*;

@Repository
public interface ICoinsReportRepository extends JpaRepository<CoinsReport, Long> {

    @Query(value = COINS_REPORT + ORDER_BY_DATE, nativeQuery = true)
    List<CoinsReport> getAllCoinsFromParty(@Param("partyId") Long partyId);

    @Query(value = ACQUIRED_COINS_REPORT + ORDER_BY_DATE, nativeQuery = true)
    List<CoinsReport> getAllAcquiredCoinsFromParty(@Param("partyId") Long partyId);

    @Query(value = EXPENDED_COINS_REPORT + " and c.state <> 'Revertido' " + ORDER_BY_DATE, nativeQuery = true)
    List<CoinsReport> getAllExpendedCoinsFromParty(@Param("partyId") Long partyId);

    @Query(value = EXPENDED_COINS_REPORT + " and c.state == 'Revertido' " + ORDER_BY_DATE, nativeQuery = true)
    List<CoinsReport> getAllRevertedCoinsFromParty(Long id);
}
