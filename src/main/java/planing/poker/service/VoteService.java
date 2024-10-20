package planing.poker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import planing.poker.domain.Vote;
import planing.poker.domain.dto.request.RequestVoteDto;
import planing.poker.domain.dto.response.ResponseVoteDto;
import planing.poker.mapper.VoteMapper;
import planing.poker.repository.VoteRepository;

import java.util.List;

@Service
public class VoteService {

    private final VoteRepository voteRepository;

    private final VoteMapper voteMapper;

    @Autowired
    public VoteService(final VoteRepository voteRepository, final VoteMapper voteMapper) {
        this.voteRepository = voteRepository;
        this.voteMapper = voteMapper;
    }

    public ResponseVoteDto createVote(final RequestVoteDto requestVoteDto) {
        return voteMapper.toDto(voteRepository.save(voteMapper.toEntity(requestVoteDto)));
    }

    public List<ResponseVoteDto> getAllVotes() {
        return voteRepository.findAll().stream().map(voteMapper::toDto).toList();
    }

    public ResponseVoteDto getVoteById(final Long id) {
        return voteRepository.findById(id).map(voteMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("message.not.find.object"));
    }

    public ResponseVoteDto updateVote(final long id, final RequestVoteDto requestVoteDto) {
        if (voteRepository.findById(id).isPresent()) {
            final Vote vote = voteMapper.toEntity(requestVoteDto);
            vote.setId(id);

            return voteMapper.toDto(voteRepository.save(vote));
        } else {
            throw new IllegalArgumentException("message.not.find.object");
        }
    }

    public void deleteVote(final Long id) {
        voteRepository.deleteById(id);
    }
}
