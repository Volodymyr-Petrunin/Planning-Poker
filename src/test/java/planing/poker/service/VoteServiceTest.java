package planing.poker.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import planing.poker.domain.Vote;
import planing.poker.domain.dto.request.RequestVoteDto;
import planing.poker.domain.dto.response.ResponseVoteDto;
import planing.poker.factory.utils.ExpectedEntityDtoUtils;
import planing.poker.factory.utils.ExpectedEntityUtils;
import planing.poker.mapper.VoteMapper;
import planing.poker.repository.VoteRepository;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    private static final ResponseVoteDto EXPECTED_DTO = ExpectedEntityDtoUtils.getVote();
    private static final Vote EXPECTED_ENTITY = ExpectedEntityUtils.getVote();

    @InjectMocks
    private VoteService voteService;

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private VoteMapper voteMapper;

    @Test
    @DisplayName("Create Vote: Should create vote and return correct DTO")
    void testCreateVote_ShouldCreateVote_AndReturnCorrectDto() {
        final RequestVoteDto requestVoteDto = new RequestVoteDto();
        when(voteMapper.toEntity(requestVoteDto)).thenReturn(EXPECTED_ENTITY);
        when(voteRepository.save(EXPECTED_ENTITY)).thenReturn(EXPECTED_ENTITY);
        when(voteMapper.toDto(EXPECTED_ENTITY)).thenReturn(EXPECTED_DTO);

        final ResponseVoteDto createdVote = voteService.createVote(requestVoteDto, any());

        assertNotNull(createdVote);

        verify(voteRepository, times(1)).save(EXPECTED_ENTITY);
        verify(voteMapper, times(1)).toEntity(requestVoteDto);
        verify(voteMapper, times(1)).toDto(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("Get All Votes: Should return all votes as a list of DTOs")
    void testGetAllVotes_ShouldReturnAllVotes() {
        when(voteRepository.findAll()).thenReturn(List.of(EXPECTED_ENTITY));
        when(voteMapper.toDto(EXPECTED_ENTITY)).thenReturn(EXPECTED_DTO);

        final List<ResponseVoteDto> votes = voteService.getAllVotes();

        assertNotNull(votes);

        verify(voteRepository, times(1)).findAll();
        verify(voteMapper, times(1)).toDto(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("Get Vote By ID: Should return the correct vote DTO for given ID")
    void testGetVoteById_ShouldReturnVote() {
        when(voteRepository.findById(EXPECTED_DTO.getId())).thenReturn(Optional.of(EXPECTED_ENTITY));
        when(voteMapper.toDto(EXPECTED_ENTITY)).thenReturn(EXPECTED_DTO);

        final ResponseVoteDto vote = voteService.getVoteById(EXPECTED_DTO.getId());

        assertNotNull(vote);

        verify(voteRepository, times(1)).findById(EXPECTED_DTO.getId());
        verify(voteMapper, times(1)).toDto(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("Get Vote By ID: Should throw exception if vote is not found")
    void testGetVoteById_ShouldThrowExceptionIfNotFound() {
        when(voteRepository.findById(EXPECTED_DTO.getId())).thenReturn(Optional.empty());

        final Exception exception = assertThrows(
                IllegalArgumentException.class, () -> voteService.getVoteById(EXPECTED_DTO.getId()));

        assertEquals("message.not.find.object", exception.getMessage());

        verify(voteRepository, times(1)).findById(EXPECTED_DTO.getId());
    }

    @Test
    @DisplayName("Update Vote: Should update vote and return the updated DTO")
    void testUpdateVote_ShouldUpdateVote_AndReturnUpdatedDto() {
        final RequestVoteDto requestVoteDto = new RequestVoteDto();
        when(voteMapper.toEntity(requestVoteDto)).thenReturn(EXPECTED_ENTITY);
        when(voteRepository.save(EXPECTED_ENTITY)).thenReturn(EXPECTED_ENTITY);
        when(voteMapper.toDto(EXPECTED_ENTITY)).thenReturn(EXPECTED_DTO);

        final ResponseVoteDto updatedVote = voteService.updateVote(1 ,requestVoteDto);

        assertNotNull(updatedVote);

        verify(voteRepository, times(1)).save(EXPECTED_ENTITY);
        verify(voteMapper, times(1)).toEntity(requestVoteDto);
        verify(voteMapper, times(1)).toDto(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("Delete Vote: Should delete vote by given ID")
    void testDeleteVote_ShouldDeleteVote() {
        doNothing().when(voteRepository).deleteById(EXPECTED_DTO.getId());

        voteService.deleteVote(EXPECTED_DTO.getId());

        verify(voteRepository, times(1)).deleteById(EXPECTED_DTO.getId());
    }
}
