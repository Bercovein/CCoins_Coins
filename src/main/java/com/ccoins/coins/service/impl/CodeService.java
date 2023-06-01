package com.ccoins.coins.service.impl;

import com.ccoins.coins.configuration.CoinStatesProperties;
import com.ccoins.coins.dto.*;
import com.ccoins.coins.exceptions.BadRequestException;
import com.ccoins.coins.exceptions.constant.ExceptionConstant;
import com.ccoins.coins.model.ClientParty;
import com.ccoins.coins.model.Code;
import com.ccoins.coins.model.Coins;
import com.ccoins.coins.model.Match;
import com.ccoins.coins.repository.IClientPartyRepository;
import com.ccoins.coins.repository.ICodesRepository;
import com.ccoins.coins.repository.ICoinsRepository;
import com.ccoins.coins.repository.IMatchRepository;
import com.ccoins.coins.service.ICodeService;
import com.ccoins.coins.utils.CodeUtils;
import com.ccoins.coins.utils.DateUtils;
import com.ccoins.coins.utils.MapperUtils;
import com.ccoins.coins.utils.enums.CodeActiveStatesEnum;
import com.ccoins.coins.utils.enums.CodeStatesEnum;
import com.ccoins.coins.utils.enums.RedeemCodeResponsesEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ccoins.coins.exceptions.constant.ExceptionConstant.*;


@Service
@Slf4j
public class CodeService implements ICodeService {

    private final IMatchRepository matchRepository;
    private final ICoinsRepository coinsRepository;
    private final ICodesRepository codesRepository;
    private final CoinStatesProperties coinStatesProperties;
    private final IClientPartyRepository clientPartyRepository;
    private final Long codeExpiration;
    private final int codeLength;

    @Autowired
    public CodeService(@Value("${code.expiration}") Long codeExpiration,
                       @Value("${code.length}") int codeLength,
                       IMatchRepository matchRepository, ICoinsRepository coinsRepository,
                       ICodesRepository codesRepository, CoinStatesProperties coinStatesProperties, IClientPartyRepository clientPartyRepository) {
        this.matchRepository = matchRepository;
        this.coinsRepository = coinsRepository;
        this.codesRepository = codesRepository;
        this.coinStatesProperties = coinStatesProperties;
        this.codeExpiration = codeExpiration;
        this.codeLength = codeLength;
        this.clientPartyRepository = clientPartyRepository;
    }

    @Override
    public ResponseEntity<List<CodeDTO>> createCodesByGameBarId(CodeRqDTO request) {

        List<CodeDTO> response = new ArrayList<>();

        try{
            Optional<Code> code = this.codesRepository.findActiveByCode(request.getCode());
            if(code.isPresent()){
                throw new BadRequestException();
            }
        }catch(Exception e){
            throw new BadRequestException(CODE_ALREADY_USED_ERROR_CODE, CODE_ALREADY_USED_ERROR);
        }

        try {
            LocalDateTime now = DateUtils.now();
            LocalDateTime expiration = now.plusMinutes(codeExpiration);

            for (int i = 0; i < request.getQuantity(); i++){

                Match match = Match.builder()
                        .active(true)
                        .startDate(now)
                        .game(request.getGame())
                        .build();

                //guarda la expiración si es requerida, sino la deja por defecto en 10 min
                if(request.isExpires()){
                    match.setEndDate(expiration);
                    if(request.getExpirationDate() != null) {
                        match.setEndDate(request.getExpirationDate());
                    }
                }

                match = this.matchRepository.save(match);

                Code code = Code.builder()
                        .prize(request.getPrizeId())
                        .points(request.getPoints())
                        .perPerson(request.isPerPerson())
                        .oneUse(request.isOneUse())
                        .match(match)
                        .build();

                //si se generó un codigo personalizado, lo guarda en el primer match
                if(i == 0 && request.getCode() != null){
                    code.setCode(request.getCode());
                }else{
                    code.setCode(CodeUtils.getRandomString(codeLength));
                }

                code = this.codesRepository.save(code);

                response.add(this.convert(code));
            }

            return ResponseEntity.ok(response);
        }catch (Exception e){
            throw new BadRequestException(NEW_CODE_ERROR_CODE, NEW_CODE_ERROR);
        }
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
    public ResponseEntity<CodeDTO> invalidateCode(StringDTO request) {
        Code code;

        try {
            Optional<Code> codeOpt = this.codesRepository.findActiveByCode(request.getText());

            if(codeOpt.isEmpty()){
                throw new BadRequestException();
            }

            code = codeOpt.get();

        }catch(Exception e){
            throw new BadRequestException(CODE_NOT_FOUND_ERROR_CODE, CODE_NOT_FOUND_ERROR);
        }

        try{
            Match match = code.getMatch();

            match.setActive(false);

            this.matchRepository.save(match);

            code.setMatch(match);

            return ResponseEntity.ok(this.convert(code));
        }catch (Exception e){
            throw new BadRequestException(INVALIDATE_CODE_ERROR_CODE, INVALIDATE_CODE_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<CodeDTO>> getByActive(Long id, String state) {

        List<Code> codes;
        List<CodeDTO> response = new ArrayList<>();

        if(CodeActiveStatesEnum.ACTIVE.getValue().equals(state)){
            codes = this.codesRepository.findActiveCodesByGameBar(id);
        }else{
            codes = this.codesRepository.findInactiveCodesByGameBar(id);
        }

        codes.forEach(code -> response.add(this.convert(code)));

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<GenericRsDTO<CoinsDTO>> redeemCode(RedeemCodeRqDTO request) {

        Optional<Code> codeOpt = this.codesRepository.findActiveByCode(request.getCode());

        Code code;

        //si no se encontró
        if(codeOpt.isEmpty()){
            return ResponseEntity.ok(new GenericRsDTO(RedeemCodeResponsesEnum.NOT_FOUND_OR_ACTIVE.getCode(), RedeemCodeResponsesEnum.NOT_FOUND_OR_ACTIVE.getMessage()));
        }

        code = codeOpt.get();
        Match match = code.getMatch();

        //si ya lo usó
        Long countByClient = this.coinsRepository.findByMatchAndClient(code.getMatch().getId(), request.getClientId());

        if(countByClient > 0){
            return ResponseEntity.ok(new GenericRsDTO(RedeemCodeResponsesEnum.ALREADY_CLAIMED.getCode(), RedeemCodeResponsesEnum.ALREADY_CLAIMED.getMessage()));
        }

        //buscar si ya fué usado por otro si es de un solo uso
        if(code.isOneUse()){
            Optional<Coins> coinsOpt = this.coinsRepository.findByMatchId(match.getId());
            if(coinsOpt.isPresent()){
                return ResponseEntity.ok(new GenericRsDTO(RedeemCodeResponsesEnum.ALREADY_CLAIMED_BY_OTHER.getCode(), RedeemCodeResponsesEnum.ALREADY_CLAIMED_BY_OTHER.getMessage()));
            }
        }

        if(!code.isPerPerson()){
            Long count = this.coinsRepository.countSameMatchByParty(match.getId(), request.getPartyId());
            if(count != 0){
                return ResponseEntity.ok(new GenericRsDTO(RedeemCodeResponsesEnum.ALREADY_CLAIMED_BY_PARTY.getCode(), RedeemCodeResponsesEnum.ALREADY_CLAIMED_BY_PARTY.getMessage()));
            }
        }

        ClientParty clientParty = this.findActiveClientPartyByClient(request.getClientId());

        if(clientParty == null){
            return ResponseEntity.ok(new GenericRsDTO(RedeemCodeResponsesEnum.CLIENT_DONT_EXIST_BY_PARTY.getCode(), RedeemCodeResponsesEnum.CLIENT_DONT_EXIST_BY_PARTY.getMessage()));
        }

        Coins coins = Coins.builder()
                .match(match)
                .clientParty(clientParty.getId())
                .active(true)
                .updatable(true)
                .dateTime(LocalDateTime.now())
                .quantity(code.getPoints() != null ? code.getPoints() : 0)
                .prize(code.getPrize())
                .state(this.coinStatesProperties.getDelivered().getName())
                .build();

        coins = this.coinsRepository.save(coins);

        CoinsDTO response = MapperUtils.map(coins,CoinsDTO.class);

        return ResponseEntity.ok(new GenericRsDTO(RedeemCodeResponsesEnum.SUCCESS.getCode(),
                RedeemCodeResponsesEnum.SUCCESS.getMessage(),
                response));

    }

    public CodeDTO convert(Code code){
        CodeDTO codeDTO = MapperUtils.map(code, CodeDTO.class);
        Match match = code.getMatch();

        codeDTO.setMatchId(match.getId());
        codeDTO.setStartDate(match.getStartDate());
        codeDTO.setEndDate(match.getEndDate());
        codeDTO.setActive(match.isActive());
        codeDTO.setGame(match.getGame());

        //por defecto no es editable, ni cerrable y está cerrado
        codeDTO.setEditable(false);
        codeDTO.setCloseable(false);
        codeDTO.setState(CodeStatesEnum.CLOSED.getValue());

        //evalua si está abierto y en fecha
        if(codeDTO.isActive() && (codeDTO.getEndDate() == null || (codeDTO.getEndDate() != null && DateUtils.isAfterNow(codeDTO.getEndDate())))){
            codeDTO.setEditable(true);
            codeDTO.setCloseable(true);
        }

        //evalua los estados del codigo
        if(codeDTO.isActive()){
            codeDTO.setState(CodeStatesEnum.OPEN.getValue());

            if(codeDTO.getEndDate() != null && DateUtils.isBeforeNow(codeDTO.getEndDate())){
                codeDTO.setState(CodeStatesEnum.EXPIRED.getValue());
            }
        }

        return codeDTO;
    }

}
