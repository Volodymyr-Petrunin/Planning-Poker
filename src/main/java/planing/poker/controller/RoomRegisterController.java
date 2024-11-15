package planing.poker.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import planing.poker.common.CustomBinderInitializer;
import planing.poker.domain.dto.request.RequestRoomDto;
import planing.poker.domain.dto.request.RequestStoryDto;
import planing.poker.domain.dto.request.RequestUserDto;
import planing.poker.domain.dto.response.ResponseRoomDto;
import planing.poker.domain.dto.response.ResponseUserDto;
import planing.poker.security.UserDetailsImpl;
import planing.poker.service.RoomService;

@Controller
@RequestMapping(RoomRegisterController.BASE_URL)
public class RoomRegisterController {

    public static final String BASE_URL = "/room";

    private static final String CREATE_ROOM_URL = "/create";

    private static final String CREATE_ROOM_PAGE = "register/room/reg-room";

    private static final String ROOM = "room";

    private final RoomService roomService;

    private final CustomBinderInitializer customBinder;

    @Autowired
    public RoomRegisterController(final RoomService roomService, final CustomBinderInitializer customBinder) {
        this.roomService = roomService;
        this.customBinder = customBinder;
    }

    @GetMapping(CREATE_ROOM_URL)
    public String createRoom(@ModelAttribute(ROOM) final RequestRoomDto room, final Model model) {
        model.addAttribute(ROOM, room);
        model.addAttribute("user", new RequestUserDto());
        return CREATE_ROOM_PAGE;
    }

    @PostMapping(CREATE_ROOM_URL)
    public String createRoom(@Valid @ModelAttribute(ROOM) final RequestRoomDto room, final Model model,
                             final BindingResult bindingResult, @AuthenticationPrincipal final UserDetailsImpl userDetails) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(ROOM, room);
            return CREATE_ROOM_PAGE;
        }

        final ResponseRoomDto result = roomService.createRoom(room, userDetails.getUsername());
        return "redirect:/room/"+ result.getRoomCode();
    }

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        customBinder.registerCustomEditor(binder, "invitedUsers", ResponseUserDto[].class);
        customBinder.registerCustomEditor(binder, "stories", RequestStoryDto[].class);
    }
}
