package planing.poker.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRoomNameRequest {

    @NotNull
    private Long roomId;

    @NotNull
    @NotBlank
    private String roomName;
}
