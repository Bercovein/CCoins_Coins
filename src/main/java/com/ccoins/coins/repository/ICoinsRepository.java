package com.ccoins.coins.repository;


import com.ccoins.coins.model.Coins;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICoinsRepository extends JpaRepository<Coins, Long> {
    @Query(value = "SELECT IFNULL(SUM(c.quantity),0) FROM coins c where c.fk_party = :id", nativeQuery = true)
    Long sumQuantityByParty(Long id);
}
