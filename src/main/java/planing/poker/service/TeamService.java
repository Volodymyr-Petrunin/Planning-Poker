package planing.poker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import planing.poker.domain.dto.request.RequestTeamDto;
import planing.poker.domain.dto.response.ResponseTeamDto;
import planing.poker.mapper.TeamMapper;
import planing.poker.repository.TeamRepository;

import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    private final TeamMapper teamMapper;

    @Autowired
    public TeamService(final TeamRepository teamRepository, final TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
    }

    public ResponseTeamDto createTeam(final RequestTeamDto requestTeamDto) {
        return teamMapper.toDto(teamRepository.save(teamMapper.toEntity(requestTeamDto)));
    }

    public List<ResponseTeamDto> getAllTeams() {
        return teamRepository.findAll().stream().map(teamMapper::toDto).toList();
    }

    public ResponseTeamDto getTeamById(final Long id) {
        return teamMapper.toDto(teamRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("message.not.find.object")));
    }

    public ResponseTeamDto updateTeam(final RequestTeamDto requestTeamDto) {
        return teamMapper.toDto(teamRepository.save(teamMapper.toEntity(requestTeamDto)));
    }

    public void deleteTeam(final Long id) {
        teamRepository.deleteById(id);
    }
}
