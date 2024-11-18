package planing.poker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import planing.poker.common.ExceptionMessages;
import planing.poker.domain.Team;
import planing.poker.domain.dto.request.RequestTeamDto;
import planing.poker.domain.dto.response.ResponseTeamDto;
import planing.poker.mapper.TeamMapper;
import planing.poker.repository.TeamRepository;

import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    private final TeamMapper teamMapper;

    private final ExceptionMessages exceptionMessages;

    @Autowired
    public TeamService(final TeamRepository teamRepository, final TeamMapper teamMapper, final ExceptionMessages exceptionMessages) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
        this.exceptionMessages = exceptionMessages;
    }

    public ResponseTeamDto createTeam(final RequestTeamDto requestTeamDto) {
        return teamMapper.toDto(teamRepository.save(teamMapper.toEntity(requestTeamDto)));
    }

    public List<ResponseTeamDto> getAllTeams() {
        return teamRepository.findAll().stream().map(teamMapper::toDto).toList();
    }

    public ResponseTeamDto getTeamById(final Long id) {
        return teamMapper.toDto(teamRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(exceptionMessages.NO_FIND_MESSAGE())));
    }

    public ResponseTeamDto updateTeam(final long id, final RequestTeamDto requestTeamDto) {
        if (teamRepository.findById(id).isPresent()) {
            final Team team = teamMapper.toEntity(requestTeamDto);
            team.setId(id);

            return teamMapper.toDto(teamRepository.save(team));
        } else {
            throw new IllegalArgumentException(exceptionMessages.NO_FIND_MESSAGE());
        }
    }

    public void deleteTeam(final Long id) {
        teamRepository.deleteById(id);
    }
}
