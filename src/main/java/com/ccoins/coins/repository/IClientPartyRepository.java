package com.ccoins.coins.repository;


import com.ccoins.coins.model.ClientParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientPartyRepository extends JpaRepository<ClientParty, Long> {

    @Query("from ClientParty" +
            " where client = :client" +
            " and active is true")
    ClientParty findByClient(@Param("client") Long client);

    @Query(value =  "SELECT cp.FK_PARTY  FROM clients_parties cp " +
            "where cp.id = :id",nativeQuery = true)
    Long getPartyIdByClientParty(@Param("id") Long id);
}
