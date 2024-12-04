package planing.poker.domain.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import planing.poker.common.validation.NotPast;
import planing.poker.common.validation.ValidDateTime;
import planing.poker.domain.dto.EventDto;
import planing.poker.domain.dto.response.ResponseStoryDto;
import planing.poker.domain.dto.response.ResponseUserDto;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * DTO for {@link planing.poker.domain.Room}
 */

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ValidDateTime
public class RequestRoomDto {

    @NotNull
    @Size(min = 2, max = 100)
    private String roomName;

    private ResponseUserDto creator;

    @NotNull
    private List<ResponseUserDto> invitedUsers;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    @NotPast
    private LocalDate startDate;

    @NotNull
    private LocalTime startTime;

    private ResponseStoryDto currentStory;

    @Valid
    private List<RequestStoryDto> stories;

    private Duration voteDuration;

    private Boolean isActive;

    private Boolean isVotingOpen;

    @NotNull
    private Boolean isAnonymousVoting;

    private EventDto event;
}
