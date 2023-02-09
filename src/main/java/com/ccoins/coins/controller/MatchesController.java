package com.ccoins.coins.controller;

import com.ccoins.coins.dto.VotingDTO;
import com.ccoins.coins.service.IMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/match")
public class MatchesController {

    @Autowired
    private IMatchService service;

    @GetMapping("/voting/bar/{id}")
    public VotingDTO getActualVotingByBar(@PathVariable Long id){
        return this.service.getActualVotingByBarId(id);
    }

    @PostMapping("/voting")
    @ResponseStatus(OK)
    public VotingDTO saveOrUpdateVoting(@RequestBody VotingDTO request){
        return this.service.saveOrUpdateVoting(request);
    }

    @GetMapping("/voting/song/{songId}")
    public VotingDTO getVotingBySong(@PathVariable("songId") Long songId){
        return this.service.getVotingBySong(songId);
    }
}
