package planing.poker.factory.dto;

import planing.poker.domain.dto.RoomDto;
import planing.poker.domain.dto.StoryDto;
import planing.poker.domain.dto.response.ResponseUserDto;

import java.sql.Date;
import java.time.LocalTime;
import java.util.Collections;

public class RoomDtoFactory {
    private static final Long EXPECTED_ID = 1L;
    private static final String EXPECTED_CODE = "ABC123";
    private static final String EXPECTED_NAME = "Test Room";
    private static final Date EXPECTED_START_DATE = Date.valueOf("2000-01-01");
    private static final LocalTime EXPECTED_START_TIME = LocalTime.of(10, 0);
    private static final boolean EXPECTED_IS_ACTIVE = true;
    private static final boolean EXPECTED_IS_VOTING_OPEN = false;
    private static final LocalTime EXPECTED_VOTE_DURATION = LocalTime.of(0, 5);

    public static RoomDto createRoom(final ResponseUserDto creator, final StoryDto currentStory) {
        return new RoomDto()
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
}
