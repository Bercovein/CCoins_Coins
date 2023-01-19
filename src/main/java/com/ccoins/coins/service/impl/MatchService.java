package com.ccoins.coins.service.impl;

import com.ccoins.coins.dto.VotingDTO;
import com.ccoins.coins.model.Match;
import com.ccoins.coins.model.Voting;
import com.ccoins.coins.repository.IMatchRepository;
import com.ccoins.coins.repository.IVoteRepository;
import com.ccoins.coins.repository.IVotingRepository;
import com.ccoins.coins.service.IMatchService;
import com.ccoins.coins.utils.MapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
public class MatchService implements IMatchService {

    private final IMatchRepository matchRepository;
    private final IVotingRepository votingRepository;
    private final IVoteRepository voteRepository;

    @Autowired
    public MatchService(IMatchRepository matchRepository, IVotingRepository votingRepository, IVoteRepository voteRepository) {
        this.matchRepository = matchRepository;
        this.votingRepository = votingRepository;
        this.voteRepository = voteRepository;
    }

    @Override
    public VotingDTO getActualVotingByBarId(Long id){

        Optional<Voting> votingOpt = this.votingRepository.getByBarAndWinnerSongIsNull(id);
        VotingDTO response = null;
        if(votingOpt.isPresent()) {
            response = (VotingDTO) MapperUtils.map(votingOpt, VotingDTO.class);
            response.getSongs().forEach(s -> s.setVotes(this.voteRepository.countBySongId(s.getId())));
        }
        return response;
    }

    @Override
    public VotingDTO saveOrUpdateVoting(VotingDTO request) {

        Voting voting = (Voting) MapperUtils.map(request, Voting.class);
        voting = this.votingRepository.save(voting);
        Match match = (Match) MapperUtils.map(request.getMatch(), Match.class);
        this.matchRepository.save(match);

        VotingDTO response = (VotingDTO) MapperUtils.map(voting, VotingDTO.class);

        return response;
    }

}
