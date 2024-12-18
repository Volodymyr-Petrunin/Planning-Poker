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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import planing.poker.common.ExceptionMessages;
import planing.poker.domain.Team;
import planing.poker.domain.dto.request.RequestTeamDto;
import planing.poker.domain.dto.response.ResponseTeamDto;
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

    private static final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
            .plugin(new JakartaValidationPlugin())
            .defaultNotNull(true)
            .build();

    private static RequestTeamDto requestTeamDto;

    private static ResponseTeamDto expectedResponseDto;

    private static Team expectedEntity;

    @InjectMocks
    private TeamService teamService;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private TeamMapper teamMapper;

    @Mock
    private ExceptionMessages exceptionMessages;

    @BeforeAll
    static void setUp() {
        requestTeamDto = fixtureMonkey.giveMeOne(RequestTeamDto.class);
        expectedResponseDto = fixtureMonkey.giveMeOne(ResponseTeamDto.class);
        expectedEntity = fixtureMonkey.giveMeOne(Team.class);
    }

    @Test
    @DisplayName("Create Team: Should create team and return correct DTO")
    void testCreateEventMessage_ShouldCreateEventMessage_AndReturnCorrectDto() {
        when(teamMapper.toEntity(requestTeamDto)).thenReturn(expectedEntity);
        when(teamRepository.save(expectedEntity)).thenReturn(expectedEntity);
        when(teamMapper.toDto(expectedEntity)).thenReturn(expectedResponseDto);

        final ResponseTeamDto createdTeam = teamService.createTeam(requestTeamDto);

        assertNotNull(createdTeam);

        verify(teamRepository, times(1)).save(expectedEntity);
        verify(teamMapper, times(1)).toEntity(requestTeamDto);
        verify(teamMapper, times(1)).toDto(expectedEntity);
    }

    @Test
    @DisplayName("Get All Teams: Should return all teams as a list of DTOs")
    void testGetAllEventMessages_ShouldReturnAllMessages() {
        when(teamRepository.findAll()).thenReturn(List.of(expectedEntity));
        when(teamMapper.toDto(expectedEntity)).thenReturn(expectedResponseDto);

        final List<ResponseTeamDto> teams = teamService.getAllTeams();

        assertNotNull(teams);

        verify(teamRepository, times(1)).findAll();
        verify(teamMapper, times(1)).toDto(expectedEntity);
    }

    @Test
    @DisplayName("Get Team By ID: Should return the correct Team DTO for given ID")
    void testGetEventMessageById_ShouldReturnMessage() {
        when(teamRepository.findById(expectedResponseDto.getId())).thenReturn(Optional.of(expectedEntity));
        when(teamMapper.toDto(expectedEntity)).thenReturn(expectedResponseDto);

        final ResponseTeamDto team = teamService.getTeamById(expectedResponseDto.getId());

        assertNotNull(team);

        verify(teamRepository, times(1)).findById(expectedResponseDto.getId());
        verify(teamMapper, times(1)).toDto(expectedEntity);
    }

    @Test
    @DisplayName("Get Team By ID: Should throw exception if team is not found")
    void testGetEventMessageById_ShouldThrowExceptionIfNotFound() {
        when(teamRepository.findById(expectedResponseDto.getId())).thenReturn(Optional.empty());
        when(exceptionMessages.NO_FIND_MESSAGE()).thenReturn("Error Message!");

        assertThrows(IllegalArgumentException.class, () -> teamService.getTeamById(expectedResponseDto.getId()));

        verify(teamRepository, times(1)).findById(expectedResponseDto.getId());
    }

    @Test
    @DisplayName("Update Team: Should update team and return the updated DTO")
    void testUpdateEventMessage_ShouldUpdateMessage_AndReturnUpdatedDto() {
        when(teamMapper.toEntity(requestTeamDto)).thenReturn(expectedEntity);
        when(teamRepository.findById(1L)).thenReturn(Optional.of(expectedEntity));
        when(teamRepository.save(expectedEntity)).thenReturn(expectedEntity);
        when(teamMapper.toDto(expectedEntity)).thenReturn(expectedResponseDto);

        final ResponseTeamDto updatedTeam = teamService.updateTeam(1 ,requestTeamDto);

        assertNotNull(updatedTeam);

        verify(teamRepository, times(1)).save(expectedEntity);
        verify(teamMapper, times(1)).toEntity(requestTeamDto);
        verify(teamMapper, times(1)).toDto(expectedEntity);
    }

    @Test
    @DisplayName("Delete Team: Should delete team by given ID")
    void testDeleteEventMessage_ShouldDeleteMessage() {
        doNothing().when(teamRepository).deleteById(expectedResponseDto.getId());

        teamService.deleteTeam(expectedResponseDto.getId());

        verify(teamRepository, times(1)).deleteById(expectedResponseDto.getId());
    }

}