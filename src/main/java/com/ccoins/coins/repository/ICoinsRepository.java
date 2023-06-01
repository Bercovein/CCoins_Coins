package com.ccoins.coins.repository;


import com.ccoins.coins.model.Coins;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICoinsRepository extends JpaRepository<Coins, Long> {
    @Query(value = "SELECT IFNULL(SUM(c.quantity),0) FROM coins c" +
            " inner join clients_parties cp on c.fk_client_party = cp.id" +
            " where cp.fk_party = :id", nativeQuery = true)
    Long sumQuantityByParty(@Param("id") Long id);


    @Query(value = "SELECT count(c.id) FROM coins c " +
            "where c.FK_MATCH = :match " +
            "and c.FK_CLIENT_PARTY = :clientParty", nativeQuery = true)
    Long findByMatchAndClient(@Param("match") Long match,@Param("clientParty")  Long clientParty);
    
    @Query(value = "SELECT count(c.id) FROM coins c " +
            "    inner join clients_parties cp on cp.FK_CLIENT = c.FK_CLIENT_PARTY " +
            "    where c.FK_MATCH = :match " +
            "    and cp.FK_PARTY = :party", nativeQuery = true)
    Long countSameMatchByParty(@Param("match") Long match, @Param("party") Long party);

    Optional<Coins> findByMatchId(Long id);
}
