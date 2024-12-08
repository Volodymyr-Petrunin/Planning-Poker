package planing.poker.domain.dto.response;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import planing.poker.domain.dto.EventDto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

/**
 * DTO for {@link planing.poker.domain.Room}
 */

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class ResponseRoomDto {
    @Positive
    private Long id;

    @NotNull
    private String roomCode;

    @NotNull
    @Size(min = 2, max = 100)
    private String roomName;

    @NotNull
    private ResponseUserDto creator;

    @NotNull
    private List<ResponseUserDto> invitedUsers;

    @NotNull
    private Date startDate;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private ResponseStoryDto currentStory;

    @NotNull
    private List<ResponseStoryDto> stories;

    @NotNull
    private Duration voteDuration;

    @NotNull
    private Boolean isActive;

    @NotNull
    private Boolean isVotingOpen;

    @NotNull
    private LocalDateTime votingEndTime;

    @NotNull
    private Boolean isAnonymousVoting;

    @NotNull
    private EventDto event;
}