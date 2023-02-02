package com.ccoins.coins.service;

import com.ccoins.coins.dto.VoteDTO;
import com.ccoins.coins.dto.VotingDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IMatchService {

    VotingDTO getActualVotingByBarId(Long id);

    VotingDTO saveOrUpdateVoting(VotingDTO request);

    VotingDTO getVotingBySong(Long songId);

    void voteSong(VoteDTO request);

    ResponseEntity<List<Long>> getClientsIdWhoVotedSong(Long songId);
}
