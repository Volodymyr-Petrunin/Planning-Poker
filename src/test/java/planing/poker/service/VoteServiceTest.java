package planing.poker.service;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import planing.poker.common.ExceptionMessages;
import planing.poker.common.factory.EventMessageFactory;
import planing.poker.domain.Vote;
import planing.poker.domain.dto.request.RequestEventMessageDto;
import planing.poker.domain.dto.request.RequestVoteDto;
import planing.poker.domain.dto.response.ResponseUserDto;
import planing.poker.domain.dto.response.ResponseVoteDto;
import planing.poker.mapper.StoryMapper;
import planing.poker.mapper.UserMapper;
import planing.poker.mapper.VoteMapper;
import planing.poker.repository.VoteRepository;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
@DisplayName("Vote Service Tests")
class VoteServiceTest {

    private static final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
            .plugin(new JakartaValidationPlugin())
            .defaultNotNull(true)
            .build();

    private static ResponseVoteDto expectedResponseDto;

    private static RequestVoteDto expectedRequestDto;

    private static Vote expectedEntity;

    @InjectMocks
    private VoteService voteService;

    @Mock
    private UserService userService;

    @Mock
    private EventMessageService eventMessageService;

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private VoteMapper voteMapper;

    @Mock
    private ExceptionMessages exceptionMessages;

    @Mock
    private UserMapper userMapper;

    @Mock
    private StoryMapper storyMapper;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private EventMessageFactory eventMessageFactory;

    @BeforeAll
    static void setUp() {
        expectedResponseDto = fixtureMonkey.giveMeOne(ResponseVoteDto.class);
        expectedRequestDto = fixtureMonkey.giveMeOne(RequestVoteDto.class);
        expectedEntity = fixtureMonkey.giveMeOne(Vote.class);
    }

    @Test
    @DisplayName("Create Vote: Should create vote and return correct DTO")
    void testCreateVote_ShouldCreateVote_AndReturnCorrectDto() {
        when(userService.getUserByEmail(any())).thenReturn(fixtureMonkey.giveMeOne(ResponseUserDto.class));
        when(voteMapper.toEntity(expectedRequestDto)).thenReturn(expectedEntity);
        when(eventMessageFactory.createMessageVoteSubmittedIfVotingAnonymous(
                expectedRequestDto.getEventId(),
                expectedRequestDto.getVoter().getId(),
                expectedRequestDto.getStory().getTitle())
        ).thenReturn(fixtureMonkey.giveMeOne(RequestEventMessageDto.class));
        when(voteRepository.save(expectedEntity)).thenReturn(expectedEntity);
        when(voteMapper.toDto(expectedEntity)).thenReturn(expectedResponseDto);

        final ResponseVoteDto createdVote = voteService.createVote(expectedRequestDto, any());

        assertNotNull(createdVote);

        verify(voteRepository, times(1)).save(expectedEntity);
        verify(voteMapper, times(1)).toEntity(expectedRequestDto);
        verify(voteMapper, times(1)).toDto(expectedEntity);
    }

    @Test
    @DisplayName("Get All Votes: Should return all votes as a list of DTOs")
    void testGetAllVotes_ShouldReturnAllVotes() {
        when(voteRepository.findAll()).thenReturn(List.of(expectedEntity));
        when(voteMapper.toDto(expectedEntity)).thenReturn(expectedResponseDto);

        final List<ResponseVoteDto> votes = voteService.getAllVotes();

        assertNotNull(votes);

        verify(voteRepository, times(1)).findAll();
        verify(voteMapper, times(1)).toDto(expectedEntity);
    }

    @Test
    @DisplayName("Get Vote By ID: Should return the correct vote DTO for given ID")
    void testGetVoteById_ShouldReturnVote() {
        when(voteRepository.findById(expectedResponseDto.getId())).thenReturn(Optional.of(expectedEntity));
        when(voteMapper.toDto(expectedEntity)).thenReturn(expectedResponseDto);

        final ResponseVoteDto vote = voteService.getVoteById(expectedResponseDto.getId());

        assertNotNull(vote);

        verify(voteRepository, times(1)).findById(expectedResponseDto.getId());
        verify(voteMapper, times(1)).toDto(expectedEntity);
    }

    @Test
    @DisplayName("Get Vote By ID: Should throw exception if vote is not found")
    void testGetVoteById_ShouldThrowExceptionIfNotFound() {
        when(voteRepository.findById(expectedResponseDto.getId())).thenReturn(Optional.empty());
        when(exceptionMessages.NO_FIND_MESSAGE()).thenReturn("Error not find!");

        assertThrows(IllegalArgumentException.class, () -> voteService.getVoteById(expectedResponseDto.getId()));

        verify(voteRepository, times(1)).findById(expectedResponseDto.getId());
    }

    @Test
    @DisplayName("Update Vote: Should update vote and return the updated DTO")
    void testUpdateVote_ShouldUpdateVote_AndReturnUpdatedDto() {
        when(voteRepository.findById(1L)).thenReturn(Optional.of(expectedEntity));
        when(voteMapper.toEntity(expectedRequestDto)).thenReturn(expectedEntity);
        when(voteRepository.save(expectedEntity)).thenReturn(expectedEntity);
        when(voteMapper.toDto(expectedEntity)).thenReturn(expectedResponseDto);

        final ResponseVoteDto updatedVote = voteService.updateVote(1 ,expectedRequestDto);

        assertNotNull(updatedVote);

        verify(voteRepository, times(1)).save(expectedEntity);
        verify(voteMapper, times(1)).toEntity(expectedRequestDto);
        verify(voteMapper, times(1)).toDto(expectedEntity);
    }

    @Test
    @DisplayName("Delete Vote: Should delete vote by given ID")
    void testDeleteVote_ShouldDeleteVote() {
        doNothing().when(voteRepository).deleteById(expectedResponseDto.getId());

        voteService.deleteVote(expectedResponseDto.getId());

        verify(voteRepository, times(1)).deleteById(expectedResponseDto.getId());
    }
}
