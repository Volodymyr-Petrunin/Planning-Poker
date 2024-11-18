package planing.poker.common.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import planing.poker.common.exception.MessageParsingException;
import planing.poker.domain.dto.request.RequestEventMessageDto;

import java.time.LocalDateTime;

@Component
public class EventMessageFactory {

    public RequestEventMessageDto createMessageUserJoined(final Long eventId, final Long userId, final String username) {
        return createRequestEventMessageDto(eventId, userId, "exceptionMessages.event.USER_JOINED", username);
    }

    public RequestEventMessageDto createMessageUserLeft(final Long eventId, final Long userId, final String username) {
        return createRequestEventMessageDto(eventId, userId, "exceptionMessages.event.USER_LEFT", username);
    }

    public RequestEventMessageDto createMessageUserRoleChanged(final Long eventId, final Long userId, final String username, final String oldRole, final String newRole) {
        return createRequestEventMessageDto(eventId, userId, "exceptionMessages.event.USER_ROLE_CHANGED", username, oldRole, newRole);
    }

    public RequestEventMessageDto createMessageCurrentStorySelected(final Long eventId, final Long userId, final String storyTitle) {
        return createRequestEventMessageDto(eventId, userId, "exceptionMessages.event.CURRENT_STORY_SELECTED", storyTitle);
    }

    public RequestEventMessageDto createMessageVotingStarted(final Long eventId, final Long userId, final String startTime) {
        return createRequestEventMessageDto(eventId, userId, "exceptionMessages.event.VOTING_STARTED", startTime);
    }

    public RequestEventMessageDto createMessageVotingEnded(final Long eventId, final Long userId, final String endTime) {
        return createRequestEventMessageDto(eventId, userId, "exceptionMessages.event.VOTING_ENDED", endTime);
    }

    public RequestEventMessageDto createMessageVoteSubmitted(final Long eventId, final Long userId, final String username, final String score) {
        return createRequestEventMessageDto(eventId, userId, "exceptionMessages.event.VOTE_SUBMITTED", username, score);
    }

    public RequestEventMessageDto createMessageRoomCreated(final Long eventId, final Long userId, final String creationTime, final String creatorName) {
        return createRequestEventMessageDto(eventId, userId, "exceptionMessages.event.ROOM_CREATED", creationTime, creatorName);
    }

    public RequestEventMessageDto createMessageRoomClosed(final Long eventId, final Long userId, final String closingTime) {
        return createRequestEventMessageDto(eventId, userId, "exceptionMessages.event.ROOM_CLOSED", closingTime);
    }

    private RequestEventMessageDto createRequestEventMessageDto(final Long eventId, final Long userId,
                                                                final String messageKey, final Object... args) {
        final String messageArgs = serializeArgs(args);
        final LocalDateTime timestamp = LocalDateTime.now();

        return new RequestEventMessageDto(eventId, userId, messageKey, messageArgs, timestamp);
    }

    private String serializeArgs(final Object... args) {
        try {
            return args == null || args.length == 0 ? "" : new ObjectMapper().writeValueAsString(args);
        } catch (JsonProcessingException e) {
            throw new MessageParsingException("Failed to serialize message arguments", e);
        }
    }
}

