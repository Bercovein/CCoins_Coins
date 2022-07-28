package com.ccoins.coins.service.impl;

import com.ccoins.coins.exceptions.BadRequestException;
import com.ccoins.coins.exceptions.constant.ExceptionConstant;
import com.ccoins.coins.repository.ICoinsRepository;
import com.ccoins.coins.service.ICoinsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class CoinsService implements ICoinsService {

    private final ICoinsRepository repository;

    @Autowired
    public CoinsService(ICoinsRepository repository) {
        this.repository = repository;
    }


    @Override
    public Long countByParty(Long id) {
        try {
            return this.repository.sumQuantityByParty(id);
        }catch(Exception e){
            throw new BadRequestException(ExceptionConstant.COUNT_COINS_BY_PARTY_ERROR_CODE,
                    this.getClass(), ExceptionConstant.COUNT_COINS_BY_PARTY_ERROR);
        }
    }
}
