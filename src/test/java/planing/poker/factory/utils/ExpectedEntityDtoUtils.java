package planing.poker.factory.utils;

import planing.poker.domain.dto.*;
import planing.poker.domain.dto.response.*;
import planing.poker.factory.dto.*;

import java.util.List;

public class ExpectedEntityDtoUtils {
    private static final ResponseUserDto USER_ELECTOR = UserDtoFactory.createExpectedElectorDto();
    private static final ResponseUserDto USER_CREATOR = UserDtoFactory.createExpectedCreatorDto();
    private static final ResponseTeamDto TEAM_DTO = TeamDtoFactory.createResponseTeamDto(USER_CREATOR, List.of(USER_ELECTOR));
    private static final ResponseStoryDto STORY = StoryDtoFactory.createStory();
    private static final ResponseRoomDto ROOM = RoomDtoFactory.createRoom(USER_CREATOR, STORY);
    private static final ResponseVoteDto VOTE = VoteDtoFactory.createVote(USER_ELECTOR, STORY);
    private static final ResponseEventMessageDto EVENT_MESSAGE = EventMessageDtoFactory.createEventMessage(USER_ELECTOR);
    private static final EventDto EVENT = EventDtoFactory.createEvent(ROOM, List.of(EVENT_MESSAGE));

    public static ResponseUserDto getUserElector() {
        return USER_ELECTOR;
    }

    public static ResponseUserDto getUserCreator() {
        return USER_CREATOR;
    }

    public static ResponseTeamDto getTeamDto() {
        return TEAM_DTO;
    }

    public static ResponseStoryDto getStory() {
        return STORY;
    }

    public static ResponseRoomDto getRoom() {
        return ROOM;
    }

    public static ResponseVoteDto getVote() {
        return VOTE;
    }

    public static ResponseEventMessageDto getEventMessage() {
        return EVENT_MESSAGE;
    }

    public static EventDto getEvent() {
        return EVENT;
    }
}
