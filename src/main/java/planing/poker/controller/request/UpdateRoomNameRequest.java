package planing.poker.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRoomNameRequest {

    private Long roomId;

    private String roomName;
}
