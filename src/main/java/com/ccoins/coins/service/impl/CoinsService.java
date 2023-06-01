package com.ccoins.coins.service.impl;

import com.ccoins.coins.configuration.CoinStatesProperties;
import com.ccoins.coins.dto.*;
import com.ccoins.coins.exceptions.BadRequestException;
import com.ccoins.coins.exceptions.constant.ExceptionConstant;
import com.ccoins.coins.model.*;
import com.ccoins.coins.repository.*;
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

import static com.ccoins.coins.exceptions.constant.ExceptionConstant.*;


@Service
@Slf4j
public class CoinsService implements ICoinsService {

    private final ICoinsRepository coinsRepository;
    private final ICoinsReportRepository coinsReportRepository;
    private final ICoinsReportByStateRepository coinsReportStatesRepository;
    private final IMatchRepository matchRepository;
    private final PaginateUtils pagination;
    private final CoinStatesProperties coinStatesProperties;

    private final IClientPartyRepository clientPartyRepository;

    @Autowired
    public CoinsService(ICoinsRepository coinsRepository, ICoinsReportRepository coinsReportRepository, ICoinsReportByStateRepository coinsReportStatesRepository, IMatchRepository matchRepository, PaginateUtils pagination, CoinStatesProperties coinStatesProperties, IClientPartyRepository clientPartyRepository) {
        this.coinsRepository = coinsRepository;
        this.coinsReportRepository = coinsReportRepository;
        this.coinsReportStatesRepository = coinsReportStatesRepository;
        this.matchRepository = matchRepository;
        this.pagination = pagination;
        this.coinStatesProperties = coinStatesProperties;
        this.clientPartyRepository = clientPartyRepository;
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

        List<Long> response = new ArrayList<>();

        try {
            Match match = matchRepository.getById(request.getMatchId());

            request.getClients().forEach(client -> {
                Long clientAdded = this.giveCoinsToOneClient(client, match, request.getQuantity());
                if (clientAdded != null) {
                    response.add(clientAdded);
                }
            });
        }catch (Exception e){
            throw new BadRequestException(ExceptionConstant.GIVE_COINS_TO_CLIENTS_ERROR_CODE,
                    this.getClass(), ExceptionConstant.GIVE_COINS_TO_CLIENTS_ERROR);
        }

        return response;
    }

    private Long giveCoinsToOneClient(Long client, Match match, Long quantity){

        Long response = null;

        ClientParty clientParty = this.findActiveClientPartyByClient(client);

        if(clientParty != null){

            Coins coins = Coins.builder()
                    .match(match)
                    .clientParty(clientParty.getId())
                    .active(true)
                    .dateTime(LocalDateTime.now())
                    .updatable(true)
                    .quantity(quantity)
                    .state(this.coinStatesProperties.getDelivered().getName())
                    .build();
            try{
                this.coinsRepository.save(coins);
                response = client;
            }catch (Exception ignored){}
        }

        return response;
    }

    private ClientParty findActiveClientPartyByClient(Long client){

        try {
            return this.clientPartyRepository.findByClient(client);
        }catch(Exception e){
            throw new BadRequestException(ExceptionConstant.CLIENT_PARTY_FIND_ERROR_CODE,
                    this.getClass(), ExceptionConstant.CLIENT_PARTY_FIND_ERROR);
        }
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

        ClientParty clientParty = this.findActiveClientPartyByClient(request.getClientParty());

        if(clientParty == null){
            return ResponseEntity.ok(ResponseDTO.builder().code("1")
                    .message("Client doesn't exist").build());
        }

        Coins coins = Coins.builder()
                .quantity(coinsSpended)
                .prize(request.getPrizeId())
                .dateTime(DateUtils.now())
                .active(true)
                .updatable(true)
                .match(null)
                .clientParty(clientParty.getId())
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

            report.removeIf(crs -> !crs.isUpdatable() && this.coinStatesProperties.getDelivered().getName().equals(crs.getState()));

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

            if (!coinStatesProperties.getInDemand().getName().equals(coinOrPrize.getState())) {
                return ResponseEntity.ok().body(new GenericRsDTO(DELIVER_PRIZE_OR_COINS_ERROR_CODE, CoinStateResponsesEnum.WRONG_STATE.getMessage()));
            }

            coinOrPrize.setState(coinStatesProperties.getDelivered().getName());
            this.coinsRepository.save(coinOrPrize);

            Long partyId = this.clientPartyRepository.getPartyIdByClientParty(coinOrPrize.getClientParty());

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

            if (!coinStatesProperties.getInDemand().getName().equals(coinOrPrize.getState())) {
                return ResponseEntity.ok().body(new GenericRsDTO(DELIVER_PRIZE_OR_COINS_ERROR_CODE, CoinStateResponsesEnum.WRONG_STATE.getMessage()));
            }

            coinOrPrize.setState(coinStatesProperties.getCancelled().getName());
            coinOrPrize.setUpdatable(false);
            this.coinsRepository.save(coinOrPrize);

            this.createAdjustment(coinOrPrize);

            Long partyId = this.clientPartyRepository.getPartyIdByClientParty(coinOrPrize.getClientParty());

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

            if (coinStatesProperties.getInDemand().getName().equals(coinOrPrize.getState())) {
                return ResponseEntity.ok().body(new GenericRsDTO(DELIVER_PRIZE_OR_COINS_ERROR_CODE, CoinStateResponsesEnum.WRONG_STATE.getMessage()));
            }

            if(!coinOrPrize.isUpdatable()){
                return ResponseEntity.ok().body(new GenericRsDTO(COIN_NOT_UPDATABLE_ERROR_CODE, CoinStateResponsesEnum.NOT_ADJUSTABLE_STATE.getMessage()));
            }

            this.createAdjustment(coinOrPrize);
            coinOrPrize.setUpdatable(false);
            this.coinsRepository.save(coinOrPrize);
            Long partyId = this.clientPartyRepository.getPartyIdByClientParty(coinOrPrize.getClientParty());

            return ResponseEntity.ok(new GenericRsDTO(null, CoinStateResponsesEnum.SUCCESSFULLY_ADJUSTED.getMessage(),partyId));
        }catch (Exception e){
            return ResponseEntity.ok().body(new GenericRsDTO(DELIVER_PRIZE_OR_COINS_ERROR_CODE, CoinStateResponsesEnum.ERROR_STATE.getMessage()));
        }
    }

    @Override
    public ResponseEntity<GenericRsDTO<List<CoinsReportStates>>> getNotDemandedReport(Long id) {

        List<CoinsReportStates> list = this.getStateReport(id, this.coinStatesProperties.getNotDemandList());

        if (list.isEmpty()) {
            return ResponseEntity.ok(new GenericRsDTO<>("", "No hay peticiones disponibles", list));
        }

        list.removeIf(crs -> !crs.getUpdatable() && this.coinStatesProperties.getDelivered().getName().equals(crs.getState()));

        return ResponseEntity.ok(new GenericRsDTO<>("", "", list));
    }

    @Override
    public ResponseEntity<GenericRsDTO<List<CoinsReportStates>>> getInDemandReport(Long id) {

        List<CoinsReportStates> list = this.getStateReport(id,this.coinStatesProperties.getDemandList());

        if (list.isEmpty()) {
            return ResponseEntity.ok(new GenericRsDTO<>("", "No hay peticiones disponibles", list));
        }

        return ResponseEntity.ok(new GenericRsDTO<>("", "", list));
    }

    @Override
    public ResponseEntity<LongDTO> countInDemandReport(Long id) {

        LongDTO response = LongDTO.builder().value(0L).build();

        List<CoinsReportStates> list = this.coinsReportStatesRepository.getAllCoinsByStateListOrderByDate(id, this.coinStatesProperties.getDemandList());

        if(list != null){
            response.setValue((long)list.size());
        }

        return ResponseEntity.ok(response);
    }

    @Override
    public List<CoinsReportStates> getStateReport(Long id, List<String> states) {
        try {
            return this.coinsReportStatesRepository.getAllCoinsByStateListOrderByDate(id, states);
        }catch (Exception e){
            return new ArrayList<>();
        }
    }


    void createAdjustment(Coins coinOrPrize){

        Coins coinsToAdjust = Coins.builder()
                .id(null)
                .clientParty(coinOrPrize.getClientParty())
                .match(coinOrPrize.getMatch())
                .active(coinOrPrize.isActive())
                .updatable(false)
                .dateTime(DateUtils.now())
                .prize(coinOrPrize.getPrize())
                .quantity(coinOrPrize.getQuantity() * -1)
                .state(coinStatesProperties.getAdjustment().getName())
                .build();

        this.coinsRepository.save(coinsToAdjust);
    }
}
