package com.ccoins.coins.repository.query;

public class CoinsReportStates {
    
    public static final String STATE_COINS_REPORT = "select ID, START_DATE, TABLE_NUMBER, NAME, STATE, UPDATABLE from (select c.id as ID, c.start_date as START_DATE, bt.number as TABLE_NUMBER, pr.name as NAME, c.state AS STATE, c.updatable AS UPDATABLE " +
            "from coins c " +
            "inner join clients_parties cp on cp.FK_CLIENT = c.FK_CLIENT_PARTY " +
            "inner join parties p on p.id = cp.FK_PARTY " +
            "inner join bar_tables bt on bt.id = p.FK_TABLE " +
            "inner join bars b on b.ID = bt.FK_BAR " +
            "inner join prizes pr on pr.id = c.FK_PRIZE " +
            "where c.state IN (:list) " +
            "and b.ID = :id " +
            "and c.START_DATE > (now() - interval 24 hour) " +
            "order by c.start_date desc) AS COINS_REPORT_STATES";
}
