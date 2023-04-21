package com.ccoins.coins.service;

import com.ccoins.coins.dto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ICoinsService {

    Long countByParty(Long id);

    List<Long> giveCoinsToClients(CoinsToWinnersDTO request);

    @Transactional
    ResponseEntity<ResponseDTO> spendCoinsInPrizeByParty(SpendCoinsRqDTO request);

    ResponseEntity<CoinsReportDTO> getAllCoinsFromParty(Long id, Pageable pagination, String type);

    ResponseEntity<List<String>> getAllStates();

    ResponseEntity<List<String>> getActiveStates();

    ResponseEntity<ResponseDTO> deliverPrizeOrCoins(Long id);

    ResponseEntity<ResponseDTO> cancelPrizeOrCoins(Long id);

    ResponseEntity<ResponseDTO> adjustPrizeOrCoins(Long id);
}
