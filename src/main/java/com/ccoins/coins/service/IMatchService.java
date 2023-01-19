package com.ccoins.coins.service;

import com.ccoins.coins.dto.VotingDTO;

public interface IMatchService {

    VotingDTO getActualVotingByBarId(Long id);

    VotingDTO saveOrUpdateVoting(VotingDTO request);
}
