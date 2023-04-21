package com.ccoins.coins.repository;


import com.ccoins.coins.model.Coins;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ICoinsRepository extends JpaRepository<Coins, Long> {
    @Query(value = "SELECT IFNULL(SUM(c.quantity),0) FROM coins c" +
            " inner join clients_parties cp on c.fk_client_party = cp.fk_client" +
            " where cp.fk_party = :id", nativeQuery = true)
    Long sumQuantityByParty(@Param("id") Long id);

    @Query(value =  "SELECT cp.FK_PARTY  FROM clients_parties cp " +
            "where cp.fk_client = :id",nativeQuery = true)
    Long getPartyIdByClient(@Param("id") Long id);

}
