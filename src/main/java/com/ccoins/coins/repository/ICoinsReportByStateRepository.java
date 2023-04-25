package com.ccoins.coins.repository;


import com.ccoins.coins.model.CoinsReportStates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ccoins.coins.repository.query.CoinsReportStates.STATE_COINS_REPORT;

@Repository
public interface ICoinsReportByStateRepository extends JpaRepository<CoinsReportStates, Long> {

    @Query(value = STATE_COINS_REPORT, nativeQuery = true)
    List<CoinsReportStates> getAllCoinsByStateListOrderByDate(@Param("id") Long id, @Param("list") List<String> states);

}
