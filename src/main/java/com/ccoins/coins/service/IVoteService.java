package com.ccoins.coins.service;

import com.ccoins.coins.dto.VoteDTO;
import com.ccoins.coins.dto.VotingDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IVoteService {

    VotingDTO getActualVotingByBarId(Long id);


    VotingDTO saveVoting(VotingDTO request);

    VotingDTO updateVoting(VotingDTO request);

    VotingDTO getVotingBySong(Long songId);

    void voteSong(VoteDTO request);

    ResponseEntity<List<Long>> getClientsIdWhoVotedSong(Long songId);

    boolean hasVotedAlready(String userIp, Long barId);

    void closeVotingByTime(Integer maxVotingTime);

    void closeVotingByBarId(Long barId);

}
