package com.ccoins.coins.repository;


import com.ccoins.coins.model.Coins;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICoinsRepository extends JpaRepository<Coins, Long> {
    @Query("SELECT SUM(c.quantity) FROM Coins c where c.party = :id")
    Long sumQuantityByParty(Long id);
}
