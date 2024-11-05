package planing.poker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import planing.poker.domain.User;
import planing.poker.domain.dto.response.ResponseRoomDto;
import planing.poker.security.UserDetailsImpl;
import planing.poker.service.RoomService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(MainController.BASE_URL)
public class MainController {

    public static final String BASE_URL = "/";

    private static final String BASE_PAGE = "main/main-page";

    private static final String ROOMS_CREATED = "roomsCreated";

    private static final String ROOMS_INVITED = "roomsInvited";

    private final RoomService roomService;

    @Autowired
    public MainController(final RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping(BASE_URL)
    public String index(@AuthenticationPrincipal final UserDetailsImpl userDetails, final Model model) {
        final User user;

        if (userDetails != null) {
            user = userDetails.getUser(User.class);
        } else {
            user = null;
        }

        final List<ResponseRoomDto> roomsRelatedToUser = roomService.getRoomsRelatedToUser(user);

        if (!roomsRelatedToUser.isEmpty()) {
            final List<ResponseRoomDto> roomsCreated = roomsRelatedToUser.stream()
                    .filter(room -> room.getCreator().getId().equals(user.getId()))
                    .toList();

            final List<ResponseRoomDto> roomsInvited = new ArrayList<>(roomsRelatedToUser);
            roomsInvited.removeAll(roomsCreated);

            model.addAttribute(ROOMS_CREATED, roomsCreated);
            model.addAttribute(ROOMS_INVITED, roomsInvited);
        }

        return BASE_PAGE;
    }
}
