package planing.poker.factory.utils;

import planing.poker.domain.dto.*;
import planing.poker.domain.dto.response.ResponseUserDto;
import planing.poker.factory.dto.*;

import java.util.List;

public class ExpectedEntityDtoUtils {
    private static final ResponseUserDto USER_ELECTOR = UserDtoFactory.createExpectedElectorDto();
    private static final ResponseUserDto USER_CREATOR = UserDtoFactory.createExpectedCreatorDto();
    private static final StoryDto STORY = StoryDtoFactory.createStory();
    private static final RoomDto ROOM = RoomDtoFactory.createRoom(USER_CREATOR, STORY);
    private static final VoteDto VOTE = VoteDtoFactory.createVote(USER_ELECTOR, STORY);
    private static final EventMessageDto EVENT_MESSAGE = EventMessageDtoFactory.createEventMessage(USER_ELECTOR);
    private static final EventDto EVENT = EventDtoFactory.createEvent(ROOM, List.of(EVENT_MESSAGE));

    public static ResponseUserDto getUserElector() {
        return USER_ELECTOR;
    }

    public static ResponseUserDto getUserCreator() {
        return USER_CREATOR;
    }

    public static StoryDto getStory() {
        return STORY;
    }

    public static RoomDto getRoom() {
        return ROOM;
    }

    public static VoteDto getVote() {
        return VOTE;
    }

    public static EventMessageDto getEventMessage() {
        return EVENT_MESSAGE;
    }

    public static EventDto getEvent() {
        return EVENT;
    }
}
