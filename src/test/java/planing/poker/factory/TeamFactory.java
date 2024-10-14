package planing.poker.factory;

import planing.poker.domain.Team;
import planing.poker.domain.User;

import java.util.List;

public class TeamFactory {

    private static final Long TEAM_ID = 1L;

    private static final String TEAM_NAME = "Best Team";

    public static Team createTeam(final User leader, final List<User> members) {
        return new Team(TEAM_ID, TEAM_NAME, leader, members);
    }
}
