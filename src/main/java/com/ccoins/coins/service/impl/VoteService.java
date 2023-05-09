package com.ccoins.coins.service.impl;

import com.ccoins.coins.dto.MatchDTO;
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
import com.ccoins.coins.service.IVoteService;
import com.ccoins.coins.utils.DateUtils;
import com.ccoins.coins.utils.MapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ccoins.coins.exceptions.constant.ExceptionConstant.*;


@Service
@Slf4j
public class VoteService implements IVoteService {

    private final IMatchRepository matchRepository;
    private final IVotingRepository votingRepository;

    private final ISongRepository songRepository;
    private final IVoteRepository voteRepository;

    @Value("${code.expiration}")
    private Long codeExpiration;

    @Value("${code.length}")
    private int codeLength;

    @Autowired
    public VoteService(IMatchRepository matchRepository, IVotingRepository votingRepository, ISongRepository songRepository, IVoteRepository voteRepository) {
        this.matchRepository = matchRepository;
        this.votingRepository = votingRepository;
        this.songRepository = songRepository;
        this.voteRepository = voteRepository;
    }

    @Override
    public VotingDTO getActualVotingByBarId(Long id){

        Optional<Voting> votingOpt = this.votingRepository.getByBarAndWinnerSongIsNull(id);
        VotingDTO response = null;
        List<SongDTO> songs = new ArrayList<>();
        if(votingOpt.isPresent()) {
            Voting voting = votingOpt.get();
            voting.getSongs().forEach(s -> {
                Long votes = (long) this.voteRepository.countBySongId(s.getId());
                SongDTO songDTO = SongDTO.builder().id(s.getId()).name(s.getName()).uri(s.getUri()).votes(votes).build();
                songs.add(songDTO);
            });
            response = VotingDTO.builder()
                    .id(voting.getId())
                    .winnerSong(SongDTO.convert(voting.getWinnerSong()))
                    .songs(songs)
                    .match(MatchDTO.convert(voting.getMatch()))
                    .build();
        }
        return response;
    }

    @Override
    public VotingDTO saveVoting(VotingDTO request) {

        try{
            //deberia guardar la votación, con sus canciones y el match
            Match match = MapperUtils.map(request.getMatch(), Match.class);
            match = this.matchRepository.save(match);

            List<Song> songs = new ArrayList<>();

            request.getSongs().forEach(songDTO -> {
                Song song = MapperUtils.map(songDTO, Song.class);
                songs.add(song);
            });

            Voting voting = Voting.builder().match(match).winnerSong(null).build();

            voting = this.votingRepository.save(voting);

            List<SongDTO> songDTOList = new ArrayList<>();

            Voting finalVoting = voting;
            songs.forEach(s -> {
                s.setVoting(finalVoting);
                s = this.songRepository.save(s);
                songDTOList.add(SongDTO.builder().id(s.getId()).name(s.getName()).uri(s.getUri()).votes(0L).build());
            });

            return VotingDTO.builder()
                    .id(voting.getId())
                    .match(MapperUtils.map(match, MatchDTO.class))
                    .songs(songDTOList)
                    .winnerSong(null)
                    .build();

        }catch(Exception e){
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public VotingDTO updateVoting(VotingDTO request) {

        try {
            Match match = MapperUtils.map(request.getMatch(), Match.class);
            this.matchRepository.save(match);

            this.votingRepository.updateWinnerSong(request.getId(), request.getWinnerSong().getId());
            return request;
        }catch(Exception e){
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public VotingDTO getVotingBySong(Long songId) {

        Optional<Voting> votingOpt = this.votingRepository.findBySongId(songId);

        if(votingOpt.isEmpty()){
            throw new ForbiddenException(SONG_NOT_FOUND_ERROR_CODE, SONG_NOT_FOUND_ERROR);
        }

        Voting voting = votingOpt.get();
        SongDTO winnerSong = null;

        if(voting.getWinnerSong() != null){

            winnerSong = SongDTO.convert(voting.getWinnerSong());
        }

        List<SongDTO> list = new ArrayList<>();

        voting.getSongs().forEach(s -> list.add(SongDTO.convert(s)));

        return VotingDTO.builder()
                .id(voting.getId())
                .winnerSong(winnerSong)
                .match(MapperUtils.map(voting.getMatch(), MatchDTO.class))
                .songs(list)
                .build();
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

    @Override
    public boolean hasVotedAlready(String userIp, Long barId) {

        return this.voteRepository.hasVotedAlready(userIp, barId);
    }

    @Override
    public void closeVotingByTime(Integer maxVotingTime) {
        this.votingRepository.closeVotingByTime(maxVotingTime);
    }

    @Override
    public void closeVotingByBarId(Long barId) {
        this.votingRepository.closeVotingByBarId(barId);
    }

}
