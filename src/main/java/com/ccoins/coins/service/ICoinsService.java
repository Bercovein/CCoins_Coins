package com.ccoins.coins.service;

import com.ccoins.coins.dto.CoinsToWinnersDTO;

public interface ICoinsService {

    Long countByParty(Long id);

    void giveCoinsToClients(CoinsToWinnersDTO request);
}
