package planing.poker.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import planing.poker.domain.dto.EventDto;
import planing.poker.domain.dto.StoryDto;
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
public class RequestRoomDto {

    @NotNull
    @Size(min = 2, max = 100)
    private String roomName;

    private ResponseUserDto creator;

    @NotNull
    private List<ResponseUserDto> invitedUsers;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalTime startTime;

    private StoryDto currentStory;

    // TODO create creator for stories
    private List<StoryDto> stories;

    private Duration voteDuration;

    private Boolean isActive;

    private Boolean isVotingOpen;

    private EventDto event;
}
