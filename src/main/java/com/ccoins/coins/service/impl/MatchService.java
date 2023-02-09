package com.ccoins.coins.service.impl;

import com.ccoins.coins.dto.SongDTO;
import com.ccoins.coins.dto.VoteDTO;
import com.ccoins.coins.dto.VotingDTO;
import com.ccoins.coins.exceptions.ForbiddenException;
import com.ccoins.coins.model.Match;
import com.ccoins.coins.model.Song;
import com.ccoins.coins.model.Vote;
import com.ccoins.coins.model.Voting;
import com.ccoins.coins.repository.IMatchRepository;
import com.ccoins.coins.repository.ISongRepository;
import com.ccoins.coins.repository.IVoteRepository;
import com.ccoins.coins.repository.IVotingRepository;
import com.ccoins.coins.service.IMatchService;
import com.ccoins.coins.utils.DateUtils;
import com.ccoins.coins.utils.MapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.ccoins.coins.exceptions.constant.ExceptionConstant.ALREADY_VOTED_ERROR;
import static com.ccoins.coins.exceptions.constant.ExceptionConstant.ALREADY_VOTED_ERROR_CODE;


@Service
@Slf4j
public class MatchService implements IMatchService {

    private final IMatchRepository matchRepository;
    private final IVotingRepository votingRepository;

    private final ISongRepository songRepository;
    private final IVoteRepository voteRepository;

    @Autowired
    public MatchService(IMatchRepository matchRepository, IVotingRepository votingRepository, ISongRepository songRepository, IVoteRepository voteRepository) {
        this.matchRepository = matchRepository;
        this.votingRepository = votingRepository;
        this.songRepository = songRepository;
        this.voteRepository = voteRepository;
    }

    @Override
    public VotingDTO getActualVotingByBarId(Long id){

        Optional<Voting> votingOpt = this.votingRepository.getByBarAndWinnerSongIsNull(id);
        VotingDTO response = null;
        if(votingOpt.isPresent()) {
            response = MapperUtils.map(votingOpt, VotingDTO.class);
            response.getSongs().forEach(s -> s.setVotes(this.voteRepository.countBySongId(s.getId())));
        }
        return response;
    }

    @Override
    public VotingDTO saveOrUpdateVoting(VotingDTO request) {

        Match match = MapperUtils.map(request.getMatch(), Match.class);
        match = this.matchRepository.save(match);
        Voting voting = MapperUtils.map(request, Voting.class);
        voting.setMatch(match);
        voting = this.votingRepository.save(voting);

        VotingDTO response = MapperUtils.map(voting, VotingDTO.class);
        Voting finalVoting = voting;
        request.getSongs().forEach(songDTO -> {
            Song song = MapperUtils.map(songDTO, Song.class);
            song.setVoting(finalVoting);
            song = this.songRepository.save(song);
            response.getSongs().add(MapperUtils.map(song, SongDTO.class));
        });

        return response;
    }

    @Override
    public VotingDTO getVotingBySong(Long songId) {

        Song song = this.songRepository.getById(songId);
        return MapperUtils.map(song.getVoting(), VotingDTO.class);
    }

    @Override
    @Transactional
    public void voteSong(VoteDTO request) {

        Song song = this.songRepository.findById(request.getSong()).get();
        Vote vote = Vote.builder().song(song).client(request.getClient()).dateTime(DateUtils.now()).build();

        Optional<List<Vote>> opt = this.voteRepository.getByClientAndSongVotingId(request.getClient() , song.getVoting().getId());

        //valida si el cliente ya votó o no
        if(opt.isEmpty() || opt.get().isEmpty()){
            this.voteRepository.save(vote);
        }else{
            throw new ForbiddenException(ALREADY_VOTED_ERROR_CODE, ALREADY_VOTED_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Long>> getClientsIdWhoVotedSong(Long songId) {
        return ResponseEntity.ok(this.voteRepository.findClientIdByVotedSong(songId));
    }

}
