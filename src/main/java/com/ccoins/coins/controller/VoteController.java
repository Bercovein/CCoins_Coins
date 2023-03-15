package com.ccoins.coins.controller;

import com.ccoins.coins.dto.VoteDTO;
import com.ccoins.coins.service.IMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/clients/song/{songId}")
    public ResponseEntity<List<Long>> getClientsIdWhoVotedSong(@PathVariable("songId") Long songId){
        return this.service.getClientsIdWhoVotedSong(songId);
    }

    @GetMapping("/client/{userIp}/bar/{barId}")
    boolean hasVotedAlready(@PathVariable("userIp") String userIp, @PathVariable("barId")  Long barId){
        return this.service.hasVotedAlready(userIp,barId);
    }
}
