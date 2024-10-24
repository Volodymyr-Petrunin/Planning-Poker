package planing.poker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import planing.poker.domain.dto.response.ResponseRoomDto;
import planing.poker.service.RoomService;

@Controller
@RequestMapping(ShowRoomController.BASE_URL)
public class ShowRoomController {

    public static final String BASE_URL = "/room";

    private static final String SHOW_ROOM_URL = "/{roomCode}";

    private static final String SHOW_ROOM_PAGE = "/show/room/show-room";

    private static final String ROOM = "room";

    private final RoomService roomService;

    private final RoomWebSocketController webSocketController;

    @Autowired
    public ShowRoomController(final RoomService roomService, final RoomWebSocketController webSocketController) {
        this.roomService = roomService;
        this.webSocketController = webSocketController;
    }

    @GetMapping(SHOW_ROOM_URL)
    public String showRoomsPage(@PathVariable final String roomCode, final Model model) {
        final ResponseRoomDto room = roomService.getRoomByCode(roomCode);
        model.addAttribute(ROOM, room);

        webSocketController.sendRoomUpdate(room);

        return SHOW_ROOM_PAGE;
    }
}