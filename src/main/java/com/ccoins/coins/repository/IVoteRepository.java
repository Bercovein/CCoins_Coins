package com.ccoins.coins.repository;


import com.ccoins.coins.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IVoteRepository extends JpaRepository<Vote, Long> {

    Long countBySongId(@Param("id") Long id);
}
