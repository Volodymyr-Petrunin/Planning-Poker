package planing.poker.common.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import planing.poker.common.exception.MessageParsingException;
import planing.poker.domain.dto.request.RequestEventMessageDto;

import java.time.LocalDateTime;
import java.util.Locale;

@Component
public class EventMessageFactory {

    private final MessageSource messageSource;

    @Autowired
    public EventMessageFactory(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public RequestEventMessageDto createMessageUserJoined(final Long eventId, final Long userId, final String username) {
        return createRequestEventMessageDto(eventId, userId, "messages.event.USER_JOINED", username);
    }

    public RequestEventMessageDto createMessageUserLeft(final Long eventId, final Long userId, final String username) {
        return createRequestEventMessageDto(eventId, userId, "messages.event.USER_LEFT", username);
    }

    public RequestEventMessageDto createMessageUserRoleChanged(final Long eventId, final Long userId, final String username, final String oldRole, final String newRole) {
        return createRequestEventMessageDto(eventId, userId, "messages.event.USER_ROLE_CHANGED", username, getLocalizedRole(oldRole), getLocalizedRole(newRole));
    }

    public RequestEventMessageDto createMessageCurrentStorySelected(final Long eventId, final Long userId, final String storyTitle) {
        return createRequestEventMessageDto(eventId, userId, "messages.event.CURRENT_STORY_SELECTED", storyTitle);
    }

    public RequestEventMessageDto createMessageCurrentStoryRemoved(final Long eventId, final Long userId, final String nickname) {
        return createRequestEventMessageDto(eventId, userId, "messages.event.CURRENT_STORY_REMOVED", nickname);
    }

    public RequestEventMessageDto createMessageVotingStarted(final Long eventId, final Long userId, final String username) {
        return createRequestEventMessageDto(eventId, userId, "messages.event.VOTING_STARTED", username);
    }

    public RequestEventMessageDto createMessageVotingEnded(final Long eventId, final Long userId, final String username) {
        return createRequestEventMessageDto(eventId, userId, "messages.event.VOTING_ENDED", username);
    }

    public RequestEventMessageDto createMessageVoteSubmitted(final Long eventId, final Long userId, final String username, final String score) {
        return createRequestEventMessageDto(eventId, userId, "messages.event.VOTE_SUBMITTED", username, score);
    }

    public RequestEventMessageDto createMessageVoteSubmittedIfVotingAnonymous(final Long eventId, final Long userId, final String storyName) {
        return createRequestEventMessageDto(eventId, userId, "messages.event.VOTE_SUBMITTED_ANONYMOUS", storyName);
    }

    public RequestEventMessageDto createMessageRoomCreated(final Long eventId, final Long userId, final String creationTime, final String creatorName) {
        return createRequestEventMessageDto(eventId, userId, "messages.event.ROOM_CREATED", creationTime, creatorName);
    }

    public RequestEventMessageDto createMessageRoomClosed(final Long eventId, final Long userId, final String closingTime) {
        return createRequestEventMessageDto(eventId, userId, "messages.event.ROOM_CLOSED", closingTime);
    }

    public RequestEventMessageDto createMessageRoomNameChanged(final Long eventId, final Long userId, final String oldName, final String newName) {
        return createRequestEventMessageDto(eventId, userId, "messages.event.ROOM_NAME_CHANGED", oldName, newName);
    }

    private RequestEventMessageDto createRequestEventMessageDto(final Long eventId, final Long userId,
                                                                final String messageKey, final Object... args) {
        final String messageArgs = serializeArgs(args);
        final LocalDateTime timestamp = LocalDateTime.now();

        return new RequestEventMessageDto(eventId, userId, messageKey, messageArgs, timestamp);
    }

    private String getLocalizedRole(final String roleName) {
        return messageSource.getMessage("role." + roleName, null, Locale.getDefault());
    }

    private String serializeArgs(final Object... args) {
        try {
            return args == null || args.length == 0 ? "" : new ObjectMapper().writeValueAsString(args);
        } catch (JsonProcessingException e) {
            throw new MessageParsingException("Failed to serialize message arguments", e);
        }
    }
}

