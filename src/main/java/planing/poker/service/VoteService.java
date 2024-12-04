package planing.poker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import planing.poker.common.ExceptionMessages;
import planing.poker.common.factory.EventMessageFactory;
import planing.poker.domain.Vote;
import planing.poker.domain.dto.request.RequestVoteDto;
import planing.poker.domain.dto.response.ResponseUserDto;
import planing.poker.domain.dto.response.ResponseVoteDto;
import planing.poker.event.vote.VoteCreatedEvent;
import planing.poker.mapper.StoryMapper;
import planing.poker.mapper.UserMapper;
import planing.poker.mapper.VoteMapper;
import planing.poker.repository.VoteRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VoteService {

    private final VoteRepository voteRepository;

    private final VoteMapper voteMapper;

    private final ExceptionMessages exceptionMessages;

    private final UserService userService;

    private final EventMessageService eventMessageService;

    private final EventMessageFactory eventMessageFactory;

    private final StoryMapper storyMapper;

    private final UserMapper userMapper;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public VoteService(final VoteRepository voteRepository, final VoteMapper voteMapper,
                       final ExceptionMessages exceptionMessages, final UserService userService,
                       final EventMessageService eventMessageService, final EventMessageFactory eventMessageFactory,
                       final StoryMapper storyMapper, final UserMapper userMapper,
                       final ApplicationEventPublisher applicationEventPublisher) {
        this.voteRepository = voteRepository;
        this.voteMapper = voteMapper;
        this.exceptionMessages = exceptionMessages;
        this.userService = userService;
        this.eventMessageService = eventMessageService;
        this.eventMessageFactory = eventMessageFactory;
        this.storyMapper = storyMapper;
        this.userMapper = userMapper;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public ResponseVoteDto createVote(final RequestVoteDto requestVoteDto, final String email) {
        final ResponseUserDto voter = userService.getUserByEmail(email);
        requestVoteDto.setVoter(voter);

        final Vote vote = voteMapper.toEntity(requestVoteDto);

        final Optional<Vote> existingVote = voteRepository.findByVoterAndStory(
                userMapper.responseToEntity(voter),
                storyMapper.responseDtoToEntity(requestVoteDto.getStory())
        );

        final ResponseVoteDto responseVoteDto = existingVote
                .map(existing -> {
                    existing.setPoints(requestVoteDto.getPoints());
                    return voteMapper.toDto(voteRepository.save(existing));
                })
                .orElseGet(() -> voteMapper.toDto(voteRepository.save(vote)));

        applicationEventPublisher.publishEvent(new VoteCreatedEvent(responseVoteDto, requestVoteDto.getRoomCode()));

        createMessageVoteSubmitted(requestVoteDto);

        return responseVoteDto;
    }

    public List<ResponseVoteDto> getAllVotes() {
        return voteRepository.findAll().stream().map(voteMapper::toDto).toList();
    }

    public ResponseVoteDto getVoteById(final Long id) {
        return voteRepository.findById(id).map(voteMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException(exceptionMessages.NO_FIND_MESSAGE()));
    }

    public ResponseVoteDto updateVote(final long id, final RequestVoteDto requestVoteDto) {
        if (voteRepository.findById(id).isPresent()) {
            final Vote vote = voteMapper.toEntity(requestVoteDto);
            vote.setId(id);

            return voteMapper.toDto(voteRepository.save(vote));
        } else {
            throw new IllegalArgumentException(exceptionMessages.NO_FIND_MESSAGE());
        }
    }

    public void deleteVote(final Long id) {
        voteRepository.deleteById(id);
    }

    private void createMessageVoteSubmitted(final RequestVoteDto requestVoteDto) {
        if (requestVoteDto.getIsAnonymousVoting()) {
            eventMessageService.createEventMessage(
                    eventMessageFactory.createMessageVoteSubmittedIfVotingAnonymous(
                            requestVoteDto.getEventId(),
                            requestVoteDto.getVoter().getId(),
                            requestVoteDto.getStory().getTitle()
                    )
            );
        } else {
            eventMessageService.createEventMessage(
                    eventMessageFactory.createMessageVoteSubmitted(
                            requestVoteDto.getEventId(),
                            requestVoteDto.getVoter().getId(),
                            requestVoteDto.getVoter().getNickname(),
                            requestVoteDto.getPoints().toString())
            );
        }
    }
}
