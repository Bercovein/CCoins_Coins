package com.ccoins.coins.service;

import com.ccoins.coins.dto.CoinsReportDTO;
import com.ccoins.coins.dto.CoinsToWinnersDTO;
import com.ccoins.coins.dto.ResponseDTO;
import com.ccoins.coins.dto.SpendCoinsRqDTO;
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
}
