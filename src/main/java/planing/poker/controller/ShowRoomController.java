package planing.poker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import planing.poker.domain.dto.response.ResponseRoomDto;
import planing.poker.security.UserDetailsImpl;
import planing.poker.service.RoomService;

@Controller
@RequestMapping(ShowRoomController.BASE_URL)
public class ShowRoomController {

    public static final String BASE_URL = "/room";

    private static final String SHOW_ROOM_URL = "/{roomCode}";

    private static final String SHOW_ROOM_PAGE = "/show/room/show-room";

    private static final String ROOM = "room";

    private static final String IS_CREATOR = "isCreator";

    private final RoomService roomService;

    @Autowired
    public ShowRoomController(final RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping(SHOW_ROOM_URL)
    public String showRoomsPage(@PathVariable final String roomCode, final Model model,
                                @AuthenticationPrincipal final UserDetailsImpl userDetails) {
        final ResponseRoomDto room = roomService.getRoomByCode(roomCode);
        boolean isCreator = room.getCreator().getEmail().equals(userDetails.getUsername());

        model.addAttribute(ROOM, room);
        model.addAttribute(IS_CREATOR, isCreator);

        return SHOW_ROOM_PAGE;
    }
}
