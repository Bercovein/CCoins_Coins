package com.ccoins.coins.repository;


import com.ccoins.coins.model.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICodesRepository extends JpaRepository<Code, Long> {

    @Query(value = "SELECT c.*, m.* FROM codes c " +
            " inner join matches m on m.id = c.FK_MATCH " +
            " inner join games g on g.id = m.FK_GAME " +
            " where  " +
            " g.id = :id " +
            " and m.active is true  " +
            " and (m.end_date > now() or m.end_date is null)" +
            " order by m.start_date desc", nativeQuery = true)
    List<Code> findActiveCodesByGameBar(@Param("id") Long game);

    @Query(value = "SELECT c.*, m.* FROM codes c " +
            " inner join matches m on m.id = c.FK_MATCH " +
            " inner join games g on g.id = m.FK_GAME " +
            " where  " +
            " g.id = :id " +
            " and (m.active is false " +
            " or m.end_date < now())" +
            " order by m.start_date desc", nativeQuery = true)
    List<Code> findInactiveCodesByGameBar(@Param("id") Long game);

    Optional<Code> findByMatch(Long id);

    @Query(value = "SELECT c.*, m.* FROM codes c" +
            " inner join matches m on m.id = c.FK_MATCH" +
            " inner join games g on g.id = m.FK_GAME" +
            " where" +
            " c.code = :code" +
            " and m.active is true" +
            " and (m.end_date > now() or m.end_date is null)", nativeQuery = true)
    Optional<Code> findActiveByCode(@Param("code") String code);
}
