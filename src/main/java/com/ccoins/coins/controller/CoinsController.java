package com.ccoins.coins.controller;

import com.ccoins.coins.dto.CoinsToWinnersDTO;
import com.ccoins.coins.service.ICoinsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coins")
public class CoinsController {

    private final ICoinsService service;

    @Autowired
    public CoinsController(ICoinsService service) {
        this.service = service;
    }

    @GetMapping("/party/{id}")
    public ResponseEntity<Long> countByParty(@PathVariable Long id){

        return ResponseEntity.ok(this.service.countByParty(id));
    }

    @PostMapping("/clients/match")
    public ResponseEntity<List<Long>> giveCoinsToClients(@RequestBody CoinsToWinnersDTO request){
        return ResponseEntity.ok(this.service.giveCoinsToClients(request));
    }
}
