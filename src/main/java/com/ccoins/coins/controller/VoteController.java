package com.ccoins.coins.controller;

import com.ccoins.coins.dto.VoteDTO;
import com.ccoins.coins.service.IMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/vote")
public class VoteController {

    @Autowired
    private IMatchService service;

    @PostMapping("")
    @ResponseStatus(OK)
    public void voteSong(@RequestBody VoteDTO request){
        this.service.voteSong(request);
    }
}
