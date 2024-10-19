package planing.poker.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import planing.poker.domain.Team;
import planing.poker.domain.dto.request.RequestTeamDto;
import planing.poker.domain.dto.response.ResponseTeamDto;
import planing.poker.factory.utils.ExpectedEntityDtoUtils;
import planing.poker.factory.utils.ExpectedEntityUtils;
import planing.poker.mapper.TeamMapper;
import planing.poker.repository.TeamRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("Team Service Tests")
@Sql(scripts = {"classpath:script/init_expected_data.sql"})
@Transactional
class TeamServiceTest {

    private static final ResponseTeamDto EXPECTED_DTO = ExpectedEntityDtoUtils.getTeamDto();

    private static final Team EXPECTED_ENTITY = ExpectedEntityUtils.getTeam();

    @InjectMocks
    private TeamService teamService;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private TeamMapper teamMapper;

    @Test
    @DisplayName("Create Team: Should create team and return correct DTO")
    void testCreateEventMessage_ShouldCreateEventMessage_AndReturnCorrectDto() {
        final RequestTeamDto requestTeamDto = new RequestTeamDto(EXPECTED_DTO.getName(), EXPECTED_DTO.getLeader(), EXPECTED_DTO.getMembers());
        when(teamMapper.toEntity(requestTeamDto)).thenReturn(EXPECTED_ENTITY);
        when(teamRepository.save(EXPECTED_ENTITY)).thenReturn(EXPECTED_ENTITY);
        when(teamMapper.toDto(EXPECTED_ENTITY)).thenReturn(EXPECTED_DTO);

        final ResponseTeamDto createdTeam = teamService.createTeam(requestTeamDto);

        assertNotNull(createdTeam);

        verify(teamRepository, times(1)).save(EXPECTED_ENTITY);
        verify(teamMapper, times(1)).toEntity(requestTeamDto);
        verify(teamMapper, times(1)).toDto(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("Get All Teams: Should return all teams as a list of DTOs")
    void testGetAllEventMessages_ShouldReturnAllMessages() {
        when(teamRepository.findAll()).thenReturn(List.of(EXPECTED_ENTITY));
        when(teamMapper.toDto(EXPECTED_ENTITY)).thenReturn(EXPECTED_DTO);

        final List<ResponseTeamDto> teams = teamService.getAllTeams();

        assertNotNull(teams);

        verify(teamRepository, times(1)).findAll();
        verify(teamMapper, times(1)).toDto(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("Get Team By ID: Should return the correct Team DTO for given ID")
    void testGetEventMessageById_ShouldReturnMessage() {
        when(teamRepository.findById(EXPECTED_DTO.getId())).thenReturn(Optional.of(EXPECTED_ENTITY));
        when(teamMapper.toDto(EXPECTED_ENTITY)).thenReturn(EXPECTED_DTO);

        final ResponseTeamDto team = teamService.getTeamById(EXPECTED_DTO.getId());

        assertNotNull(team);

        verify(teamRepository, times(1)).findById(EXPECTED_DTO.getId());
        verify(teamMapper, times(1)).toDto(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("Get Team By ID: Should throw exception if team is not found")
    void testGetEventMessageById_ShouldThrowExceptionIfNotFound() {
        when(teamRepository.findById(EXPECTED_DTO.getId())).thenReturn(Optional.empty());

        final Exception exception = assertThrows(
                IllegalArgumentException.class, () -> teamService.getTeamById(EXPECTED_DTO.getId()));

        assertEquals("message.not.find.object", exception.getMessage());

        verify(teamRepository, times(1)).findById(EXPECTED_DTO.getId());
    }

    @Test
    @DisplayName("Update Team: Should update team and return the updated DTO")
    void testUpdateEventMessage_ShouldUpdateMessage_AndReturnUpdatedDto() {
        final RequestTeamDto requestTeamDto = new RequestTeamDto(EXPECTED_DTO.getName(), EXPECTED_DTO.getLeader(), EXPECTED_DTO.getMembers());

        when(teamMapper.toEntity(requestTeamDto)).thenReturn(EXPECTED_ENTITY);
        when(teamRepository.save(EXPECTED_ENTITY)).thenReturn(EXPECTED_ENTITY);
        when(teamMapper.toDto(EXPECTED_ENTITY)).thenReturn(EXPECTED_DTO);

        final ResponseTeamDto updatedTeam = teamService.updateTeam(requestTeamDto);

        assertNotNull(updatedTeam);

        verify(teamRepository, times(1)).save(EXPECTED_ENTITY);
        verify(teamMapper, times(1)).toEntity(requestTeamDto);
        verify(teamMapper, times(1)).toDto(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("Delete Team: Should delete team by given ID")
    void testDeleteEventMessage_ShouldDeleteMessage() {
        doNothing().when(teamRepository).deleteById(EXPECTED_DTO.getId());

        teamService.deleteTeam(EXPECTED_DTO.getId());

        verify(teamRepository, times(1)).deleteById(EXPECTED_DTO.getId());
    }

}