package planing.poker.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import planing.poker.domain.dto.StoryDto;
import planing.poker.domain.dto.response.ResponseUserDto;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

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

    @NotNull
    private Date startDate;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private List<StoryDto> stories;

    @NotNull
    private LocalTime voteDuration;
}
