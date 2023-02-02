package com.ccoins.coins.service.impl;

import com.ccoins.coins.dto.CoinsToWinnersDTO;
import com.ccoins.coins.exceptions.BadRequestException;
import com.ccoins.coins.exceptions.constant.ExceptionConstant;
import com.ccoins.coins.model.Coins;
import com.ccoins.coins.model.Match;
import com.ccoins.coins.repository.ICoinsRepository;
import com.ccoins.coins.repository.IMatchRepository;
import com.ccoins.coins.service.ICoinsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class CoinsService implements ICoinsService {

    private final ICoinsRepository coinsRepository;
    private final IMatchRepository matchRepository;

    @Autowired
    public CoinsService(ICoinsRepository coinsRepository, IMatchRepository matchRepository) {
        this.coinsRepository = coinsRepository;
        this.matchRepository = matchRepository;
    }


    @Override
    public Long countByParty(Long id) {
        try {
            return this.coinsRepository.sumQuantityByParty(id);
        }catch(Exception e){
            throw new BadRequestException(ExceptionConstant.COUNT_COINS_BY_PARTY_ERROR_CODE,
                    this.getClass(), ExceptionConstant.COUNT_COINS_BY_PARTY_ERROR);
        }
    }

    @Override
    public List<Long> giveCoinsToClients(CoinsToWinnersDTO request) {

        Match match = matchRepository.getById(request.getMatchId());
        List<Long> response = new ArrayList<>();

        request.getClients().forEach(client -> {
            Coins coins = Coins.builder()
                    .match(match)
                    .clientParty(client)
                    .active(true)
                    .dateTime(LocalDateTime.now())
                    .quantity(request.getQuantity())
                    .build();
            try{
                this.coinsRepository.save(coins);
                response.add(client);
            }catch (Exception ignored){}
        });

        return response;
    }
}
