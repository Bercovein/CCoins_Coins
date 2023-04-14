package com.ccoins.coins.repository;


import com.ccoins.coins.model.CoinState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICoinStatesRepository extends JpaRepository<CoinState, Long> {

    List<CoinState> findByIdIn(List<Long> list);
}
