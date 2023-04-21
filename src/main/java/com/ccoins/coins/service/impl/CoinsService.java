package com.ccoins.coins.service.impl;

import com.ccoins.coins.configuration.CoinStatesProperties;
import com.ccoins.coins.dto.*;
import com.ccoins.coins.exceptions.BadRequestException;
import com.ccoins.coins.exceptions.constant.ExceptionConstant;
import com.ccoins.coins.model.Coins;
import com.ccoins.coins.model.CoinsReport;
import com.ccoins.coins.model.Match;
import com.ccoins.coins.repository.ICoinsReportRepository;
import com.ccoins.coins.repository.ICoinsRepository;
import com.ccoins.coins.repository.IMatchRepository;
import com.ccoins.coins.service.ICoinsService;
import com.ccoins.coins.utils.DateUtils;
import com.ccoins.coins.utils.PaginateUtils;
import com.ccoins.coins.utils.enums.CoinStateResponsesEnum;
import com.ccoins.coins.utils.enums.CoinsReportEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ccoins.coins.exceptions.constant.ExceptionConstant.DELIVER_PRIZE_OR_COINS_ERROR_CODE;
import static com.ccoins.coins.exceptions.constant.ExceptionConstant.GET_COINS_STATES_ERROR;


@Service
@Slf4j
public class CoinsService implements ICoinsService {

    private final ICoinsRepository coinsRepository;
    private final ICoinsReportRepository coinsReportRepository;
    private final IMatchRepository matchRepository;
    private final PaginateUtils pagination;
    private final CoinStatesProperties coinStatesProperties;

    @Autowired
    public CoinsService(ICoinsRepository coinsRepository, ICoinsReportRepository coinsReportRepository, IMatchRepository matchRepository, PaginateUtils pagination, CoinStatesProperties coinStatesProperties) {
        this.coinsRepository = coinsRepository;
        this.coinsReportRepository = coinsReportRepository;
        this.matchRepository = matchRepository;
        this.pagination = pagination;
        this.coinStatesProperties = coinStatesProperties;
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
                    .state(this.coinStatesProperties.getDelivered().getName())
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
                .state(this.coinStatesProperties.getInDemand().getName())
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

    @Override
    public ResponseEntity<CoinsReportDTO> getAllCoinsFromParty(Long id, Pageable pagination, String type) {

        List<CoinsReport> report= new ArrayList<>();
        Long quantity = this.countByParty(id);

        try {

            if (type != null && !type.isEmpty()){
                if (CoinsReportEnum.ACQUIRED.getValue().equals(type))
                    report = this.coinsReportRepository.getAllAcquiredCoinsFromParty(id);

                if (CoinsReportEnum.EXPENDED.getValue().equals(type))
                    report = this.coinsReportRepository.getAllExpendedCoinsFromParty(id);
            }
            else{
                report = this.coinsReportRepository.getAllCoinsFromParty(id);
            }

        }catch (Exception e){
            throw new BadRequestException(ExceptionConstant.COINS_REPORT_ERROR_CODE,
                    this.getClass(), ExceptionConstant.COINS_REPORT_ERROR);
        }

        CoinsReportDTO response = CoinsReportDTO.builder()
                .report(this.pagination.paginate(report, pagination))
                .totalCoins(quantity).build();

        return ResponseEntity.ok(response);

    }

    @Override
    public ResponseEntity<List<StateDTO>> getAllStates() {

        try{
            return ResponseEntity.ok(this.coinStatesProperties.getAllStateList());
        }catch(Exception e){
            log.error(GET_COINS_STATES_ERROR);
        }
        return ResponseEntity.ok(new ArrayList<>());
    }

    @Override
    public ResponseEntity<List<StateDTO>> getActiveStates() {

        try{
            return ResponseEntity.ok(this.coinStatesProperties.getEnabledStateList());
        }catch(Exception e){
            log.error(GET_COINS_STATES_ERROR);
        }
        return ResponseEntity.ok(new ArrayList<>());
    }

    @Override
    @Transactional
    public ResponseEntity<GenericRsDTO<Long>> deliverPrizeOrCoins(Long id) {

        try {
            Optional<Coins> coinOrPrizeOpt = this.coinsRepository.findById(id);

            if (coinOrPrizeOpt.isEmpty()) {
                return ResponseEntity.ok().body(new GenericRsDTO<>(DELIVER_PRIZE_OR_COINS_ERROR_CODE, CoinStateResponsesEnum.NOT_FOUND_COINS.getMessage()));
            }

            Coins coinOrPrize = coinOrPrizeOpt.get();

            if (!coinStatesProperties.getInDemand().equals(coinOrPrize.getState())) {
                return ResponseEntity.ok().body(new GenericRsDTO(DELIVER_PRIZE_OR_COINS_ERROR_CODE, CoinStateResponsesEnum.WRONG_STATE.getMessage()));
            }

            coinOrPrize.setState(coinStatesProperties.getDelivered().getName());
            this.coinsRepository.save(coinOrPrize);

            Long partyId = this.coinsRepository.getPartyIdByClient(coinOrPrize.getClientParty());

            return ResponseEntity.ok(new GenericRsDTO(null, CoinStateResponsesEnum.SUCCESSFULLY_DELIVERED.getMessage(), partyId));
        }catch (Exception e){
            return ResponseEntity.ok().body(new GenericRsDTO(DELIVER_PRIZE_OR_COINS_ERROR_CODE, CoinStateResponsesEnum.ERROR_STATE.getMessage()));
        }
    }

    @Override
    @Transactional
    public ResponseEntity<GenericRsDTO<Long>> cancelPrizeOrCoins(Long id) {
        try {
            Optional<Coins> coinOrPrizeOpt = this.coinsRepository.findById(id);

            if (coinOrPrizeOpt.isEmpty()) {
                return ResponseEntity.ok().body(new GenericRsDTO(DELIVER_PRIZE_OR_COINS_ERROR_CODE, CoinStateResponsesEnum.NOT_FOUND_COINS.getMessage()));
            }

            Coins coinOrPrize = coinOrPrizeOpt.get();

            if (!coinStatesProperties.getInDemand().equals(coinOrPrize.getState())) {
                return ResponseEntity.ok().body(new GenericRsDTO(DELIVER_PRIZE_OR_COINS_ERROR_CODE, CoinStateResponsesEnum.WRONG_STATE.getMessage()));
            }

            coinOrPrize.setState(coinStatesProperties.getCancelled().getName());
            this.coinsRepository.save(coinOrPrize);

            this.createAdjustment(coinOrPrize);

            Long partyId = this.coinsRepository.getPartyIdByClient(coinOrPrize.getClientParty());

            return ResponseEntity.ok(new GenericRsDTO<>(null, CoinStateResponsesEnum.SUCCESSFULLY_CANCELED.getMessage(),partyId));
        }catch (Exception e){
            return ResponseEntity.ok().body(new GenericRsDTO(DELIVER_PRIZE_OR_COINS_ERROR_CODE, CoinStateResponsesEnum.ERROR_STATE.getMessage()));
        }
    }

    @Override
    public ResponseEntity<GenericRsDTO<Long>> adjustPrizeOrCoins(Long id) {
        try {
            Optional<Coins> coinOrPrizeOpt = this.coinsRepository.findById(id);

            if (coinOrPrizeOpt.isEmpty()) {
                return ResponseEntity.ok().body(new GenericRsDTO(DELIVER_PRIZE_OR_COINS_ERROR_CODE, CoinStateResponsesEnum.NOT_FOUND_COINS.getMessage()));
            }

            Coins coinOrPrize = coinOrPrizeOpt.get();

            if (coinStatesProperties.getInDemand().equals(coinOrPrize.getState())) {
                return ResponseEntity.ok().body(new GenericRsDTO(DELIVER_PRIZE_OR_COINS_ERROR_CODE, CoinStateResponsesEnum.WRONG_STATE.getMessage()));
            }

            this.createAdjustment(coinOrPrize);

            Long partyId = this.coinsRepository.getPartyIdByClient(coinOrPrize.getClientParty());

            return ResponseEntity.ok(new GenericRsDTO(null, CoinStateResponsesEnum.SUCCESSFULLY_ADJUSTED.getMessage(),partyId));
        }catch (Exception e){
            return ResponseEntity.ok().body(new GenericRsDTO(DELIVER_PRIZE_OR_COINS_ERROR_CODE, CoinStateResponsesEnum.ERROR_STATE.getMessage()));
        }
    }

    void createAdjustment(Coins coinOrPrize){

        Coins coinsToAdjust = Coins.builder()
                .id(null)
                .clientParty(coinOrPrize.getClientParty())
                .match(coinOrPrize.getMatch())
                .active(coinOrPrize.isActive())
                .dateTime(DateUtils.now())
                .prize(coinOrPrize.getPrize())
                .quantity(coinOrPrize.getQuantity() * -1)
                .state(coinStatesProperties.getAdjustment().getName())
                .build();

        this.coinsRepository.save(coinsToAdjust);
    }
}
