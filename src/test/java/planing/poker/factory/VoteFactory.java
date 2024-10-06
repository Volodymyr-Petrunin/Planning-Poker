package planing.poker.factory;

import planing.poker.domain.Story;
import planing.poker.domain.User;
import planing.poker.domain.Vote;

public class VoteFactory {

    private static final Long EXPECTED_ID = 1L;
    private static final Integer EXPECTED_POINTS = 5;

    public static Vote createVote(final User voter, final Story story) {
        return new Vote()
                .setId(EXPECTED_ID)
                .setVoter(voter)
                .setPoints(EXPECTED_POINTS)
                .setStory(story);
    }
}
