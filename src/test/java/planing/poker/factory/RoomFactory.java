package planing.poker.factory;

import planing.poker.domain.Event;
import planing.poker.domain.Room;
import planing.poker.domain.Story;
import planing.poker.domain.User;
import planing.poker.factory.utils.ExpectedEntityUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

public class RoomFactory {

    private static final Long EXPECTED_ID = 1L;
    private static final String EXPECTED_CODE = "ABC123";
    private static final String EXPECTED_NAME = "Test Room";
    private static final LocalDate EXPECTED_START_DATE = LocalDate.of(2000,1,1);
    private static final LocalTime EXPECTED_START_TIME = LocalTime.of(10, 0);
    private static final boolean EXPECTED_IS_ACTIVE = true;
    private static final boolean EXPECTED_IS_VOTING_OPEN = false;
    private static final boolean EXPECTED_IS_ANONYMOUS_VOTING = false;
    private static final Duration EXPECTED_VOTE_DURATION = Duration.ofMinutes(5);

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
                .setIsAnonymousVoting(EXPECTED_IS_ANONYMOUS_VOTING)
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

    public static Room createNewRoom() {
        return new Room()
                .setId(null)
                .setRoomCode("NEW123")
                .setRoomName("New Room")
                .setCreator(ExpectedEntityUtils.getUserCreator())
                .setStartDate(LocalDate.of(2024,9,30))
                .setStartTime(LocalTime.of(12, 0))
                .setVoteDuration(Duration.ofMinutes(10))
                .setIsActive(true)
                .setIsVotingOpen(true);
    }
}
