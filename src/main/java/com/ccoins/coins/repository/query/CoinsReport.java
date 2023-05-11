package com.ccoins.coins.repository.query;

public class CoinsReport {
    
    public static final String EXPENDED_COINS_REPORT = "SELECT c.id AS COINS_ID, c.START_DATE AS DATE, c.QUANTITY AS COINS, pr.NAME AS ACTIVITY, cl.NICK_NAME AS CLIENT, c.state AS STATE, c.UPDATABLE AS UPDATABLE  " +
            "             FROM coins c " +
            "             inner join clients_parties cp on cp.FK_CLIENT = c.FK_CLIENT_PARTY " +
            "             inner join clients cl on cl.ID = cp.FK_CLIENT " +
            "             inner join prizes pr on pr.ID = c.FK_PRIZE " +
            "             where cp.FK_PARTY = :partyId";
    public static final String ACQUIRED_COINS_REPORT = "SELECT c.id AS COINS_ID, c.START_DATE AS DATE, c.QUANTITY AS COINS, g.NAME AS ACTIVITY, cl.NICK_NAME AS CLIENT, c.state AS STATE, c.UPDATABLE AS UPDATABLE FROM coins c " +
            "             inner join clients_parties cp on cp.FK_CLIENT = c.FK_CLIENT_PARTY " +
            "             inner join matches m on m.ID = c.FK_MATCH " +
            "             inner join games g on g.ID = m.FK_GAME " +
            "             inner join clients cl on cl.ID = cp.FK_CLIENT " +
            "             where cp.FK_PARTY = :partyId";
    public static final String COINS_REPORT = "select COINS_ID, DATE, COINS, ACTIVITY, CLIENT, STATE, UPDATABLE " +
            "            from (" + ACQUIRED_COINS_REPORT + " UNION " + EXPENDED_COINS_REPORT + ") AS COINS_REPORT";

    public static final String ORDER_BY_DATE = " order by DATE desc";
}
