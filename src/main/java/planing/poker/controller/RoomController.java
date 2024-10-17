package planing.poker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import planing.poker.domain.dto.request.RequestRoomDto;
import planing.poker.domain.dto.request.RequestUserDto;
import planing.poker.service.RoomService;

@Controller
@RequestMapping(RoomController.BASE_URL)
public class RoomController {

    public static final String BASE_URL = "/room";

    public static final String CREATE_ROOM_URL = "/create";

    public static final String CREATE_ROOM_PAGE = "/register/room/reg-room";

    public static final String ROOM = "room";

    private final RoomService roomService;

    @Autowired
    public RoomController(final RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping(CREATE_ROOM_URL)
    public String createRoom(@ModelAttribute(ROOM) final RequestRoomDto room, final Model model) {
        model.addAttribute(ROOM, room);
        model.addAttribute("user", new RequestUserDto());
        return CREATE_ROOM_PAGE;
    }
}
