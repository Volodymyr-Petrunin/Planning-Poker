package planing.poker.factory;

import planing.poker.domain.Event;
import planing.poker.domain.Room;
import planing.poker.domain.Story;
import planing.poker.domain.User;

import java.sql.Date;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

public class RoomFactory {

    private static final Long EXPECTED_ID = 1L;
    private static final String EXPECTED_CODE = "ABC123";
    private static final String EXPECTED_NAME = "Test Room";
    private static final Date EXPECTED_START_DATE = Date.valueOf("2000-01-01");
    private static final LocalTime EXPECTED_START_TIME = LocalTime.of(10, 0);
    private static final boolean EXPECTED_IS_ACTIVE = true;
    private static final boolean EXPECTED_IS_VOTING_OPEN = false;
    private static final LocalTime EXPECTED_VOTE_DURATION = LocalTime.of(0, 5);

    public static Room createRoom(final User creator, final Story currentStory) {
        return new Room()
                .setId(EXPECTED_ID)
                .setRoomCode(EXPECTED_CODE)
                .setRoomName(EXPECTED_NAME)
                .setCreator(creator)
                .setStartDate(EXPECTED_START_DATE)
                .setStartTime(EXPECTED_START_TIME)
                .setCurrentStory(currentStory)
                .setIsActive(EXPECTED_IS_ACTIVE)
                .setIsVotingOpen(EXPECTED_IS_VOTING_OPEN)
                .setVoteDuration(EXPECTED_VOTE_DURATION)
                .setInvitedUsers(Collections.emptyList())
                .setStories(Collections.emptyList());
    }

    public static Room createRoomWithConnections(final User creator, final List<User> invitedUsers,
                                                 final Story currentStory, final List<Story> stories,
                                                 final Event event) {
        return new Room()
                .setId(EXPECTED_ID)
                .setRoomCode(EXPECTED_CODE)
                .setRoomName(EXPECTED_NAME)
                .setCreator(creator)
                .setInvitedUsers(invitedUsers)
                .setStartDate(EXPECTED_START_DATE)
                .setStartTime(EXPECTED_START_TIME)
                .setVoteDuration(EXPECTED_VOTE_DURATION)
                .setCurrentStory(currentStory)
                .setStories(stories)
                .setEvent(event);

    }
}
