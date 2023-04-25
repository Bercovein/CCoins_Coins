package com.ccoins.coins.controller;

import com.ccoins.coins.dto.*;
import com.ccoins.coins.model.CoinsReportStates;
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
    public ResponseEntity<List<StateDTO>> getAllStates(){
        return this.service.getAllStates();
    }

    @GetMapping("/active-states")
    public ResponseEntity<List<StateDTO>> getActiveStates(){
        return this.service.getActiveStates();
    }

    @PostMapping("/{id}/deliver")
    public ResponseEntity<GenericRsDTO<Long>> deliverPrizeOrCoins(@PathVariable("id") Long id){
        return this.service.deliverPrizeOrCoins(id);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<GenericRsDTO<Long>> cancelPrizeOrCoins(@PathVariable("id") Long id){
        return this.service.cancelPrizeOrCoins(id);
    }

    @PostMapping("/{id}/adjust")
    public ResponseEntity<GenericRsDTO<Long>> adjustPrizeOrCoins(@PathVariable("id") Long id){
        return this.service.adjustPrizeOrCoins(id);
    }

    @GetMapping("/bar/{id}/ended-coins")
    public ResponseEntity<GenericRsDTO<List<CoinsReportStates>>> getNotDemandedReport(@PathVariable("id") Long id){
        return this.service.getNotDemandedReport(id);
    }

    @GetMapping("/bar/{id}/in-demand")
    public ResponseEntity<GenericRsDTO<List<CoinsReportStates>>> getInDemandReport(@PathVariable("id") Long id){
        return this.service.getInDemandReport(id);
    }
}
