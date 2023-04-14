package com.ccoins.coins.controller;

import com.ccoins.coins.dto.*;
import com.ccoins.coins.service.ICoinsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/coins")
public class CoinsController {

    private final ICoinsService service;

    @Autowired
    public CoinsController(ICoinsService service) {
        this.service = service;
    }

    @GetMapping("/party/{id}/count")
    public ResponseEntity<Long> countByParty(@PathVariable Long id){

        return ResponseEntity.ok(this.service.countByParty(id));
    }

    @PostMapping("/clients/match")
    public ResponseEntity<List<Long>> giveCoinsToClients(@RequestBody CoinsToWinnersDTO request){
        return ResponseEntity.ok(this.service.giveCoinsToClients(request));
    }

    @PostMapping("/party/prize/buy")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseDTO> spendCoinsInPrizeByParty(@RequestBody @Valid SpendCoinsRqDTO request){
        return this.service.spendCoinsInPrizeByParty(request);
    }

    @GetMapping("/party/{id}")
    public ResponseEntity<CoinsReportDTO> getAllCoinsFromParty(@PathVariable Long id,
                                                               @PageableDefault Pageable pagination,
                                                               @RequestParam(value = "type",  required = false) String type){
        return this.service.getAllCoinsFromParty(id, pagination, type);
    }

    @GetMapping("/states")
    public ResponseEntity<CoinStateListDTO> getAllStates(){
        return this.service.getAllStates();
    }

    @GetMapping("/active-states")
    public ResponseEntity<CoinStateListDTO> getActiveStates(){
        return this.service.getActiveStates();
    }
}
