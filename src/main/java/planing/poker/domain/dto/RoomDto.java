package planing.poker.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import planing.poker.domain.dto.response.ResponseUserDto;

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
public class RoomDto {
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
    private StoryDto currentStory;

    @NotNull
    private List<StoryDto> stories;

    @NotNull
    private LocalTime voteDuration;

    @NotNull
    private Boolean isActive;

    @NotNull
    private Boolean isVotingOpen;

    private EventDto event;
}