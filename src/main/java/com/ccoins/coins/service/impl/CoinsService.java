package com.ccoins.coins.service.impl;

import com.ccoins.coins.dto.CoinsToWinnersDTO;
import com.ccoins.coins.dto.ResponseDTO;
import com.ccoins.coins.dto.SpendCoinsRqDTO;
import com.ccoins.coins.exceptions.BadRequestException;
import com.ccoins.coins.exceptions.constant.ExceptionConstant;
import com.ccoins.coins.model.Coins;
import com.ccoins.coins.model.Match;
import com.ccoins.coins.repository.ICoinsRepository;
import com.ccoins.coins.repository.IMatchRepository;
import com.ccoins.coins.service.ICoinsService;
import com.ccoins.coins.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public ResponseEntity<ResponseDTO> spendCoinsInPrizeByParty(SpendCoinsRqDTO request){

        Long coinsAbleToSpend = this.countByParty(request.getPartyId());

        if(coinsAbleToSpend < request.getPrizePoints()){
            return ResponseEntity.ok(ResponseDTO.builder().code(ExceptionConstant.NO_ENOUGH_COINS_ERROR_CODE)
                    .message(ExceptionConstant.NO_ENOUGH_COINS_ERROR).build());
        }

        Long coinsSpended = request.getPrizePoints() > 0 ? request.getPrizePoints()  * - 1 : request.getPrizePoints();

        Coins coins = Coins.builder()
                .quantity(coinsSpended)
                .prize(request.getPrizeId())
                .dateTime(DateUtils.now())
                .active(true)
                .match(null)
                .clientParty(request.getClientParty())
                .build();

        try{
            this.coinsRepository.save(coins);
        }catch (Exception exception){
            return ResponseEntity.ok(ResponseDTO.builder().code(ExceptionConstant.SAVE_COINS_ERROR_CODE)
                    .message(ExceptionConstant.SAVE_COINS_ERROR).build());
        }
        return ResponseEntity.ok(ResponseDTO.builder().code("0")
                .message("Successful purchase. Congratulations!").build());
    }
}
