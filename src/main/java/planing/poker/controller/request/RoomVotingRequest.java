package planing.poker.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class RoomVotingRequest {

    @NotNull
    @Positive
    private Long roomId;

    @NotNull
    private Boolean isVotingOpen;
}
