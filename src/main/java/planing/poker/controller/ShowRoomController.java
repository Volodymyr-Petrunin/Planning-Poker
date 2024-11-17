package planing.poker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import planing.poker.common.Role;
import planing.poker.controller.request.UpdateCurrentStoryRequest;
import planing.poker.controller.request.UpdateRoomNameRequest;
import planing.poker.domain.User;
import planing.poker.domain.dto.response.ResponseRoomDto;
import planing.poker.domain.dto.response.ResponseUserDto;
import planing.poker.security.UserDetailsImpl;
import planing.poker.service.RoomService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Controller
@RequestMapping(ShowRoomController.BASE_URL)
public class ShowRoomController {

    public static final String BASE_URL = "/room";

    private static final String SHOW_ROOM_URL = "/{roomCode}";

    private static final String SHOW_ROOM_PAGE = "show/room/show-room";

    private static final String UPDATE_CURRENT_STORY_MESSAGE_MAPPING = "/updateCurrentStory";

    private static final String UPDATE_ROOM_NAME_MESSAGE_MAPPING = "/updateRoomName";

    private static final String ROOM = "room";

    private static final String IS_CREATOR = "isCreator";

    private static final String CURRENT_USER_ID = "currentUserId";

    private static final String PRESENTERS = "presenters";

    private static final String ELECTORS = "electors";

    private static final String SPECTATORS = "spectators";

    private static final String START_TIME_ISO = "startTimeIso";

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
        final User currentUser = userDetails.getUser(User.class);
        final String startTimeIso = getStartTimeIso(room);

        model.addAttribute(ROOM, room);
        model.addAttribute(IS_CREATOR, isCreator);
        model.addAttribute(CURRENT_USER_ID, currentUser.getId());
        model.addAttribute(PRESENTERS, getUsersByRoleInRoom(room, Role.USER_PRESENTER));
        model.addAttribute(ELECTORS, getUsersByRoleInRoom(room, Role.USER_ELECTOR));
        model.addAttribute(SPECTATORS, getUsersByRoleInRoom(room, Role.USER_SPECTATOR));
        model.addAttribute(START_TIME_ISO, startTimeIso);

        return SHOW_ROOM_PAGE;
    }

    @MessageMapping(UPDATE_CURRENT_STORY_MESSAGE_MAPPING)
    public void updateCurrentStory(final UpdateCurrentStoryRequest request) {
        roomService.updateCurrentStory(request.getStoryId(), request.getResponseStoryDto());
    }

    @MessageMapping(UPDATE_ROOM_NAME_MESSAGE_MAPPING)
    public void updateRoomName(final UpdateRoomNameRequest request) {
        roomService.updateRoomName(request.getRoomId(), request.getRoomName());
    }


    private List<ResponseUserDto> getUsersByRoleInRoom(final ResponseRoomDto room, final Role role) {
        final List<ResponseUserDto> users = room.getInvitedUsers().stream()
                .filter(user -> user.getRoles().stream()
                        .anyMatch(roomUserRole ->
                                roomUserRole.getRoomId().equals(room.getId()) &&
                                        roomUserRole.getRole().equals(role)))
                .toList();

        users.forEach(user -> user.setRoomRole(role));

        return users;
    }

    private static String getStartTimeIso(final ResponseRoomDto room) {
        final LocalDateTime startDateTime = LocalDateTime.of(
                room.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                room.getStartTime()
        );

        return startDateTime.atZone(ZoneId.systemDefault()).toInstant().toString();
    }
}
