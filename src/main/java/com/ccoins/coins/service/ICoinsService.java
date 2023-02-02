package com.ccoins.coins.service;

import com.ccoins.coins.dto.CoinsToWinnersDTO;

import java.util.List;

public interface ICoinsService {

    Long countByParty(Long id);

    List<Long> giveCoinsToClients(CoinsToWinnersDTO request);
}
