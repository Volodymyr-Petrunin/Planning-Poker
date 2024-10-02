package planing.poker.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

/**
 * DTO for {@link planing.poker.domain.Room}
 */

@Getter
@Setter
public class RoomDto {
    @Positive
    private Long id;

    @NotNull
    private String roomCode;

    @NotNull
    @Size(min = 2, max = 100)
    private String roomName;

    @NotNull
    private UserDto creator;

    @NotNull
    private List<UserDto> invitedUsers;

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
}